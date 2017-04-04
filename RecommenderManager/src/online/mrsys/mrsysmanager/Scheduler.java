package online.mrsys.mrsysmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class Scheduler {

    private static final Logger logger = Logger.getLogger(Scheduler.class.getName());
    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final String clientId = "Scheduler";

    private final MqttClient client;
    private final MqttConnectOptions options;

    public Scheduler() throws MqttException {
        client = new MqttClient(Protocol.BROKER, clientId, new MemoryPersistence());
        client.setCallback(new Handler());
        options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
    }

    public void connect() {
        try {
            logger.log(Level.INFO, "Connecting to {0} ...", Protocol.BROKER);
            client.connect(options);
            logger.log(Level.INFO, "Connection established");
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when connecting to " + Protocol.BROKER, e);
        }
    }

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

    public void subscribe() {
        try {
            client.subscribe(Protocol.TOPIC);
            logger.log(Level.INFO, "Start subscribing topic: {0}", Protocol.TOPIC);
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when try to subscribe topic: " + Protocol.TOPIC, e);
        }
    }

    private class Handler implements MqttCallback {

        public void onRequested(String content) {
            // content format: date!user1#user2#...
            System.out.println(content); // TODO parse content as the users to be recommended
            final String date = formatter.format(new Date());
            final File dir = new File("res/" + date);
            final File[] files = dir.listFiles((FilenameFilter) (d, name) -> name.endsWith(Protocol.RES_SUFFIX));
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
                    // format: date!user!record1#record2#...
                    final StringBuilder sb = new StringBuilder();
                    sb.append(date);
                    sb.append("!");
                    sb.append(file.getName().replaceFirst(Protocol.RES_SUFFIX, ""));
                    sb.append("!");
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

        public void onConfirmed() {
            final String cmd = "java -version";
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                logger.log(Level.SEVERE, null, e);
            }
            disconnect();
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
            } else if (content.startsWith(Protocol.CONFIRM)) {
                onConfirmed();
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
            final FileHandler fh = new FileHandler("log/" + Protocol.SYS_NAME + "-" + date + ".log", true);
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (SecurityException | IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
    
    private static long getTimeMillis(String time){
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

        return day + " d "
        + (hour < 10 ? "0" + hour : hour) + " h "
        + (min < 10 ? "0" + min : min) + " m "
        + (sec < 10 ? "0" + sec : sec) + " s";
    }
    
    public static void main(String[] args) {
        initLogger();
        String scheduleTime = "06:00:00";
        if (args.length > 0) {
            scheduleTime = args[0];
        }
        final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        final long oneDay = 24 * 60 * 60 * 1000;
        long initDelay = getTimeMillis(scheduleTime) - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        logger.log(Level.INFO, "Mrsys scheduler will start at {0}, {1} from now", new Object[] {scheduleTime, formatTime(initDelay)});
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Scheduler scheduler = new Scheduler();
                    scheduler.connect();
                    scheduler.subscribe();
                } catch (MqttException e) {
                    logger.log(Level.SEVERE, null, e);
                }
            }
        }, initDelay, oneDay, TimeUnit.MILLISECONDS);
    }

}
