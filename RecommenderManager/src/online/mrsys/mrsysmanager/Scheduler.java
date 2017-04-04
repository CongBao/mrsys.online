package online.mrsys.mrsysmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private static final String clientId = "Scheduler";

    private final MqttClient client;
    private final MqttConnectOptions options;

    private final SimpleDateFormat formatter;

    public Scheduler() throws MqttException {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        initLogger();
        client = new MqttClient(Protocol.BROKER, clientId, new MemoryPersistence());
        client.setCallback(new Handler());
        options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
    }

    public void initLogger() {
        try {
            String date = formatter.format(new Date());
            FileHandler fh = new FileHandler("log/" + Protocol.SYS_NAME + "-" + date + ".log", true);
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (SecurityException | IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
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
        String msg = protocol + content;
        MqttMessage message = new MqttMessage();
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

        public void onRequested() {
            final String date = formatter.format(new Date());
            final File dir = new File("res/" + date);
            final File[] files = dir.listFiles((FilenameFilter) (d, name) -> name.endsWith(Protocol.RES_SUFFIX));
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
                    // format: date!author!record1#record2#...
                    StringBuilder sb = new StringBuilder();
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
                onRequested();
            } else if (content.startsWith(Protocol.CONFIRM)) {
                onConfirmed();
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            logger.log(Level.INFO, "Message {0} deliveried", token.getTopics());
        }

    }

    public static void main(String[] args) {
        try {
            Scheduler scheduler = new Scheduler();
            scheduler.connect();
            scheduler.subscribe();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
