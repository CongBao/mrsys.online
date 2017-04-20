package online.mrsys.movierecommender.schedule;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import online.mrsys.movierecommender.util.PathLoader;

public class CleanZombiesJob extends QuartzJobBean {
    
    private static final Logger logger = Logger.getLogger(CleanZombiesJob.class.getName());
    
    private boolean isRunning = false;

    @Override
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
        if (!isRunning) {
            isRunning = true;
            logger.log(Level.INFO, "Start cleaning zombies job");
            File file = null;
            try {
                file = new File(PathLoader.fetch(PathLoader.USER_BUF));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error when loading file", e);
                return;
            }
            if (!file.exists()) {
                logger.log(Level.WARNING, "File not found: {0}", file.getAbsolutePath());
                return;
            }
            Properties prop = new Properties();
            try (InputStream in = new BufferedInputStream(new FileInputStream(file));) {
                prop.load(in);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error when reading file", e);
            }
            prop.stringPropertyNames().forEach(user -> {
                int freq = Integer.parseInt(prop.getProperty(user));
                if (--freq <= 0) {
                    prop.remove(user);
                } else {
                    prop.setProperty(user, String.valueOf(freq));
                }
            });
            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file));) {
                prop.store(out, "User=Frequency");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error when writing file", e);
            }
            logger.log(Level.INFO, "Cleaning zombies job completed, next time: {0}", ctx.getNextFireTime());
            isRunning = false;
        }
    }

}
