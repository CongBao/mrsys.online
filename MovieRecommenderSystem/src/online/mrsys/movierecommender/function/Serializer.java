package online.mrsys.movierecommender.function;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serializer {
    
    private static final Logger logger = Logger.getLogger(Serializer.class.getName());

    public static byte[] serialize(Object obj) {
        byte[] result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            result = baos.toByteArray();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when serializing object", e);
        }
        return result;
    }
    
    public static Object deserialize(byte[] buf) {
        Object result = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            ObjectInputStream ois = new ObjectInputStream(bais);
            result = ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.SEVERE, "Error when deserializing bytes", e);
        }
        return result;
    }
    
}
