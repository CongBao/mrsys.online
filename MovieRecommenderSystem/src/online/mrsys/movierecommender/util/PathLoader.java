package online.mrsys.movierecommender.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PathLoader {
    
    public static final String PATH = "/home/mrsys.online/filepath.properties";
    public static final String DATA_BUF = "DataBuffer";
    public static final String USER_BUF = "UserBuffer";
    
    public static String fetch(String name) throws IOException {
        File file = new File(PATH);
        if (!file.exists()) {
            throw new FileNotFoundException("Cannot find property file: " + PATH);
        }
        Properties prop = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        prop.load(in);
        String path = prop.getProperty(name);
        in.close();
        return path;
    }

}
