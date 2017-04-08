package online.mrsys.movierecommender.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An utility class to seralize or deserialize objects.
 * 
 * @author Cong Bao
 *
 */
public class Serializer {

    private static final Logger logger = Logger.getLogger(Serializer.class.getName());

    /**
     * Serialize an object to bytes.
     * 
     * @param obj
     *            the object to be serialized
     * @return an array of bytes of the serialized object or null if any error occurs
     */
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

    /**
     * Deserialize bytes into an object.
     * 
     * @param buf
     *            the bytes of a serialized object
     * @return the object deserialized or null if any error occurs
     */
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
