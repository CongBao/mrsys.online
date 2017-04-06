package online.mrsys.mrsysmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import online.mrsys.common.remote.Protocol;

/**
 * This class is used to receive requests from server and handle them locally at
 * a fixed time everyday. The procedure of this schedule is shown below:
 * <ol>
 * <li>The scheduler wakes up at a fixed time (6 a.m. by default), connect to
 * MQTT broker and subscribe an assigned topic (MRSYSCOMMUNICATION by
 * default).</li>
 * <li>The server wakes up at a fixed time (6 a.m. by default), connect to MQTT
 * broker, subscribe an assigned topic (MRSYSCOMMUNICATION by default), and
 * publish a request with a list of users that should be processed in the next
 * 24 hours to this topic.</li>
 * <li>The scheduler receives server's request, store the list of users to be
 * processed, and publish a serial of results it processed in the last 24 hours
 * to the topic.</li>
 * <li>The server receives the scheduler's results, publish a serial of update
 * of ratings if there are any, and publish confirm to the topic. After
 * everything is done, the server updates users' recommendation list in
 * database, and finally disconnect from the MQTT broker and wait for the next
 * waking up.</li>
 * <li>The scheduler receives the updates and store them locally if there are
 * any. When the confirm is received, the scheduler starts the script and
 * disconnects from the MQTT broker, waiting for next waking up.</li>
 * </ol>
 * 
 * @since JDK1.8
 * @author Cong Bao
 *
 */
public class Scheduler {

    private static final Logger logger = Logger.getLogger(Scheduler.class.getName());
    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final String clientId = "Scheduler";

    private final MqttClient client;
    private final MqttConnectOptions options;

    private static Process process;

    public Scheduler() throws MqttException {
        client = new MqttClient(Protocol.BROKER, clientId, new MemoryPersistence());
        client.setCallback(new Handler());
        options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
    }

    /**
     * Connect to the MQTT broker.
     */
    public void connect() {
        try {
            logger.log(Level.INFO, "Connecting to {0} ...", Protocol.BROKER);
            client.connect(options);
            logger.log(Level.INFO, "Connection established");
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when connecting to " + Protocol.BROKER, e);
        }
    }

    /**
     * Disconnect from MQTT broker. The disconnecting is processed in a new
     * thread so it can be called every where.
     */
    public void disconnect() {
        if (client.isConnected()) {
            new Thread(() -> {
                try {
                    logger.log(Level.INFO, "Disconnecting from {0} ...", Protocol.BROKER);
                    client.disconnect();
                    logger.log(Level.INFO, "Disconnected");
                } catch (MqttException e) {
                    logger.log(Level.SEVERE, "Error when disconnected from " + Protocol.BROKER, e);
                }
            }).start();
        }
    }

    /**
     * Publish a topic to the topic assigned in {@link Protocol}.
     * 
     * @param protocol
     *            the protocol specified in {@link Protocol}
     * @param content
     *            the content to publish
     */
    public void publish(String protocol, String content) {
        final String msg = protocol + content;
        final MqttMessage message = new MqttMessage();
        message.setPayload(msg.getBytes());
        message.setQos(2);
        message.setRetained(true);
        try {
            client.publish(Protocol.TOPIC, message);
            logger.log(Level.INFO, "Start publishing topic: {0}", Protocol.TOPIC);
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when try to publish topic: " + Protocol.TOPIC, e);
        }
    }

    /**
     * Subscribe a topic assigned in {@link Protocol}.
     */
    public void subscribe() {
        try {
            client.subscribe(Protocol.TOPIC);
            logger.log(Level.INFO, "Start subscribing topic: {0}", Protocol.TOPIC);
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when try to subscribe topic: " + Protocol.TOPIC, e);
        }
    }

    /**
     * A callback class to handle message received.
     * 
     * @author Cong Bao
     *
     */
    private class Handler implements MqttCallback {

        private String userList;
        private List<String> newRatings;
        private List<String> updatedRatings;

        /**
         * When a message starts with {@link Protocol.REQUEST} received.
         * 
         * @param content
         *            the content of this message with format date@(user_id#)+
         */
        public void onRequested(String content) {
            // content format: date@user1#user2#...
            logger.log(Level.INFO, "Request received: {0}", content);
            userList = content;
            final String date = formatter.format(new Date());
            final File dir = new File("res/" + date);
            final File[] files = dir.listFiles((FilenameFilter) (d, name) -> name.endsWith(Protocol.RES_SUFFIX));
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
                    // format: date@user@record1#record2#...
                    final StringBuilder sb = new StringBuilder();
                    sb.append(date);
                    sb.append("@");
                    sb.append(file.getName().replaceFirst(Protocol.RES_SUFFIX, ""));
                    sb.append("@");
                    reader.lines().forEach(line -> {
                        sb.append(line);
                        sb.append("#");
                    });
                    publish(Protocol.RESULT, sb.toString());
                } catch (IOException e) {
                    logger.log(Level.SEVERE, null, e);
                }
            }
        }

        /**
         * When a message starts with {@link Protocol.UPDATE} received.
         * 
         * @param content
         *            the content of this message with format
         *            (new|update)user_id#movie_id#rating
         */
        public void onUpdated(String content) {
            // content format: (new|update)user_id#movie_id#rating
            logger.log(Level.INFO, "Update received: {0}", content);
            if (content.startsWith(Protocol.NEW_PREFIX)) {
                content = content.replaceFirst(Protocol.NEW_PREFIX, "");
                newRatings = new ArrayList<>();
                newRatings.add(content);
            } else if (content.startsWith(Protocol.UPDATE_PREFIX)) {
                content = content.replaceFirst(Protocol.UPDATE_PREFIX, "");
                updatedRatings = new ArrayList<>();
                updatedRatings.add(content);
            }
        }

        /**
         * When a message starts with {@link Protocol.CONFIRM} received.
         * 
         * @param content
         *            the content of this message
         */
        public void onConfirmed(String content) {
            logger.log(Level.INFO, "Confirm received: {0}", content);
            if (newRatings != null && newRatings.size() > 0) {
                updateLocalFile(Protocol.NEW_PREFIX, newRatings);
            }
            if (updatedRatings != null && updatedRatings.size() > 0) {
                updateLocalFile(Protocol.UPDATE_PREFIX, updatedRatings);
            }
            final String[] cmd = { "/bin/bash", "-c", "python recommend.py " + userList };
            logger.log(Level.INFO, "Start running python script, command: {0}", cmd);
            try {
                process = Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                logger.log(Level.SEVERE, null, e);
            }
            disconnect();
        }

        /**
         * Update the rating file.
         * 
         * @param protocol
         *            the protocol specified in {@link Protocol}
         * @param toUpdate
         *            a list of updates
         */
        public void updateLocalFile(String protocol, List<String> toUpdate) {
            File file = new File("data/ratings.csv");
            if (!file.exists()) {
                logger.log(Level.WARNING, "File not exist: {0}", file.getAbsolutePath());
                return;
            }
            if (protocol.equals(Protocol.NEW_PREFIX)) {
                try (BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(file, true)));) {
                    toUpdate.forEach(item -> {
                        String res = item.replaceAll("#", ",");
                        try {
                            writer.write(res);
                            writer.newLine();
                        } catch (IOException e) {
                            logger.log(Level.SEVERE, "Error when writing file", e);
                        }
                    });
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error when opening file", e);
                }
            } else if (protocol.equals(Protocol.UPDATE_PREFIX)) {
                File tmp = new File("data/ratings.csv.tmp");
                if (!file.renameTo(tmp)) {
                    logger.log(Level.SEVERE, "Error when renaming file: {0}", file.getAbsolutePath());
                    return;
                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tmp)));
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(new FileOutputStream(file)));) {
                    List<String> updates = new ArrayList<>(toUpdate);
                    reader.lines().forEachOrdered(line -> {
                        try {
                            for (String item : updates) {
                                if (line.split(",")[0].equals(item.split("#")[0])) {
                                    updates.remove(item);
                                    writer.write(item.replaceAll("#", ","));
                                    writer.newLine();
                                    return;
                                }
                            }
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            logger.log(Level.SEVERE, "Error when writing file", e);
                        }
                    });
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error when opening file", e);
                } finally {
                    tmp.delete();
                }
            }
            logger.log(Level.INFO, "Local file updated");
        }

        @Override
        public void connectionLost(Throwable cause) {
            logger.log(Level.WARNING, "Connection lost", cause);
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            if (!topic.equalsIgnoreCase(Protocol.TOPIC)) {
                logger.log(Level.WARNING, "Unknown topic: {0}", topic);
                return;
            }
            String content = new String(message.getPayload(), "ISO-8859-1");
            if (content.startsWith(Protocol.REQUEST)) {
                onRequested(content.replaceFirst(Protocol.REQUEST, ""));
            } else if (content.startsWith(Protocol.UPDATE)) {
                onUpdated(content.replaceFirst(Protocol.UPDATE, ""));
            } else if (content.startsWith(Protocol.CONFIRM)) {
                onConfirmed(content.replaceFirst(Protocol.CONFIRM, ""));
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            logger.log(Level.INFO, "Message {0} deliveried", token.getTopics());
        }

    }

    private static void initLogger() {
        try {
            final String date = formatter.format(new Date());
            new File("log/").mkdirs();
            final String path = "log/" + Protocol.SYS_NAME + "-" + date + ".log";
            new File(path).createNewFile();
            final FileHandler fh = new FileHandler(path, true);
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (SecurityException | IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date current = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return current.getTime();
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Error when parse date", e);
        }
        return 0;
    }

    private static String formatTime(long millis) {
        final int ss = 1000;
        final int mm = ss * 60;
        final int HH = mm * 60;
        final int dd = HH * 24;

        long day = millis / dd;
        long hour = (millis - day * dd) / HH;
        long min = (millis - day * dd - hour * HH) / mm;
        long sec = (millis - day * dd - hour * HH - min * mm) / ss;

        return day + " d " + (hour < 10 ? "0" + hour : hour) + " h " + (min < 10 ? "0" + min : min) + " m "
                + (sec < 10 ? "0" + sec : sec) + " s";
    }

    /**
     * The main function to start the scheduling.
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        initLogger();
        String scheduleTime = "21:02:00";
        if (args.length > 0) {
            scheduleTime = args[0];
        }
        final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        final long oneDay = 24 * 60 * 60 * 1000;
        long initDelay = getTimeMillis(scheduleTime) - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        logger.log(Level.INFO, "Mrsys scheduler will start at {0}, {1} from now",
                new Object[] { scheduleTime, formatTime(initDelay) });
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (process != null && process.isAlive()) {
                    process.destroy();
                }
                try {
                    Scheduler scheduler = new Scheduler();
                    scheduler.connect();
                    scheduler.subscribe();
                } catch (MqttException e) {
                    logger.log(Level.SEVERE, "Error when initializing scheduler", e);
                }
            }
        }, initDelay, oneDay, TimeUnit.MILLISECONDS);
    }

}
