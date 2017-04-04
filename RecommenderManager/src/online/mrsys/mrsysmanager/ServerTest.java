package online.mrsys.mrsysmanager;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import online.mrsys.common.remote.Protocol;

public class ServerTest {
    
    private static final Logger logger = Logger.getLogger(ServerTest.class.getName());

    private static final String clientId = "ServerTest";

    private final MqttClient client;
    private final MqttConnectOptions options;

    private final SimpleDateFormat formatter;
    
    public ServerTest() throws MqttException {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
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
        
        public void onResulted(String content) {
            System.out.println(content);
            publish(Protocol.CONFIRM, "OK");
//            disconnect();
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
            if (content.startsWith(Protocol.RESULT)) {
                onResulted(content.replaceFirst(Protocol.RESULT, ""));
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            logger.log(Level.INFO, "Message {0} deliveried", token.getTopics());
        }
        
    }

    public static void main(String[] args) {
        try {
            ServerTest test = new ServerTest();
            test.connect();
            test.subscribe();
            test.publish(Protocol.REQUEST, "help");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
