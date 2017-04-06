package online.mrsys.movierecommender.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;

public class MovieRecommender {
	
    private static final Logger logger = Logger.getLogger(MovieRecommender.class.getName());

    private static final String clientId = "Recommender";

    private final MqttClient client;
    private final MqttConnectOptions options;
    
    private final UserManager userManager;

    private final SimpleDateFormat formatter;
    
    private Map<String, List<String>> recomList = new HashMap<>(100);
    private boolean scheduling = true;
    
    public MovieRecommender(UserManager userManager) throws MqttException {
        this.userManager = userManager;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        client = new MqttClient(Protocol.BROKER, clientId, new MemoryPersistence());
        client.setCallback(new Handler());
        options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
    }
    
    public void recommend(List<User> next) {
        new Thread(() -> {
            connect();
            subscribe();
            // publish request with user id list
            // format: date!user1#user2#...
            final String date = formatter.format(new Date());
            final StringBuilder sb = new StringBuilder();
            sb.append(date);
            sb.append("@");
            next.forEach(user -> {
                sb.append(user.getId());
                sb.append("#");
            });
            publish(Protocol.REQUEST, sb.toString());
        }).start();
        while (scheduling) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        if (recomList.size() > 0) {
            updateDatabase(recomList);
        }
    }
    
    public void updateDatabase(Map<String, List<String>> recommend) {
        logger.log(Level.INFO, "Start updating database...");
        recommend.forEach((k, v) -> {
            User user = userManager.getUserById(Integer.parseInt(k));
            if (user == null) {
                logger.log(Level.WARNING, "User with id {0} not found", k);
                return;
            }
            byte[] serializedRecom = Serializer.serialize(v);
            if (serializedRecom != null) {
                userManager.updateRecommendation(user, serializedRecom);
            }
        });
        logger.log(Level.INFO, "Database updated");
    }
    
    private void connect() {
        try {
            logger.log(Level.INFO, "Connecting to {0} ...", Protocol.BROKER);
            client.connect(options);
            logger.log(Level.INFO, "Connection established");
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when connecting to " + Protocol.BROKER, e);
        }
    }

    private void disconnect() {
        try {
            logger.log(Level.INFO, "Disconnecting from {0} ...", Protocol.BROKER);
            client.disconnect();
            logger.log(Level.INFO, "Disconnected");
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when disconnected from " + Protocol.BROKER, e);
        }
    }
    
    private void cleanBroker() {
        try {
            client.publish(Protocol.TOPIC, new byte[0], 2, true);
            logger.log(Level.INFO, "Broker cleaned");
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when cleaning broker", e);
        }
    }

    private void publish(String protocol, String content) {
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

    private void subscribe() {
        try {
            client.subscribe(Protocol.TOPIC);
            logger.log(Level.INFO, "Start subscribing topic: {0}", Protocol.TOPIC);
        } catch (MqttException e) {
            logger.log(Level.SEVERE, "Error when try to subscribe topic: " + Protocol.TOPIC, e);
        }
    }

    private class Handler implements MqttCallback {

        private int timeout = 1000;
        private boolean isRuning = false;

        public void onResulted(String content) {
            // content format: date@user@record1#record2#...
            logger.log(Level.INFO, "Result received: {0}", content);
            String[] module = content.split("@");
            String date = module[0];
            if (!date.equalsIgnoreCase(formatter.format(new Date()))) {
                logger.log(Level.WARNING, "The date of recieved results is {0}, but today is {1}", new Object[] { date, formatter.format(new Date()) });
                return;
            }
            String user = module[1];
            String[] movies = module[2].split("#");
            recomList.put(user, Arrays.asList(movies));
            timeout = 1000;
            countdown();
        }

        public void countdown() {
            if (isRuning) {
                return;
            }
            isRuning = true;
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    timeout -= 100;
                    if (timeout <= 0) {
                        break;
                    }
                }
                List<String> data = getDataBuffer();
                if (data != null && !data.isEmpty()) {
                    data.forEach(item -> publish(Protocol.UPDATE, item));
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                publish(Protocol.CONFIRM, "OK");
                cleanBroker();
                disconnect();
                isRuning = false;
                scheduling = false;
            }).start();
        }
        
        public List<String> getDataBuffer() {
            final File file = new File("data.buf");
            if (!file.exists()) {
                logger.log(Level.INFO, "No buffer file");
                return null;
            }
            final List<String> updates = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                reader.lines().forEach(updates::add);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error when reading updates", e);
            }
            file.delete();
            logger.log(Level.INFO, "Buffer file deleted");
            return updates;
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
    
}
