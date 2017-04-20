package online.mrsys.movierecommender.aop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import online.mrsys.common.remote.Protocol;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.util.PathLoader;

/**
 * A class monitoring the save and update actions of {@link Rating} entities using AOP.
 * It will create a buffer file locally containing the information of updated
 * ratings.
 * 
 * @author Cong Bao
 *
 */
public class DataChecker {

    private static final Logger logger = Logger.getLogger(DataChecker.class.getName());

    /**
     * Triggered after the save action of entity is successfully returned.
     * <p>Cut Point: execution(* online.mrsys.common.dao.BaseDao.save(..)) && args(entity)
     * 
     * @param entity
     *            the entity been saved
     */
    public void afterSave(Object entity) {
        if (!(entity instanceof Rating)) {
            return;
        }
        final Rating rating = (Rating) entity;
        logger.log(Level.INFO, "New rating record detected: {0}", rating);
        final StringBuilder sb = new StringBuilder(Protocol.NEW_PREFIX);
        sb.append(rating.getUser().getId());
        sb.append("#");
        sb.append(rating.getMovie().getId());
        sb.append("#");
        sb.append(rating.getRating());
        bufferData(sb.toString());
        bufferUser(rating.getUser().getId().toString());
        logger.log(Level.INFO, "New rating record has written into buffer");
    }

    /**
     * Triggered after the update action of entity is successfully returned.
     * <p>Cut Point: execution(* online.mrsys.common.dao.BaseDao.update(..)) && args(entity)
     * 
     * @param entity
     *            the entity been updated
     */
    public void afterUpdate(Object entity) {
        if (!(entity instanceof Rating)) {
            return;
        }
        final Rating rating = (Rating) entity;
        logger.log(Level.INFO, "Update in rating record detected: {0}", rating);
        final StringBuilder sb = new StringBuilder(Protocol.UPDATE_PREFIX);
        sb.append(rating.getUser().getId());
        sb.append("#");
        sb.append(rating.getMovie().getId());
        sb.append("#");
        sb.append(rating.getRating());
        bufferData(sb.toString());
        bufferUser(rating.getUser().getId().toString());
        logger.log(Level.INFO, "Updates of rating record have written into buffer");
    }

    private void bufferData(String content) {
        File file = null;
        try {
            file = new File(PathLoader.fetch(PathLoader.DATA_BUF));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when loading file", e);
            return;
        }
        if (!file.exists()) {
            logger.log(Level.WARNING, "File not found: {0}", file.getAbsolutePath());
            try {
                logger.log(Level.INFO, "Creating file: {0}", file.getAbsolutePath());
                file.createNewFile();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error when creating file", e);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when writing file", e);
        }
    }
    
    private void bufferUser(String user) {
        File file = null;
        try {
            file = new File(PathLoader.fetch(PathLoader.USER_BUF));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when loading file", e);
            return;
        }
        if (!file.exists()) {
            logger.log(Level.WARNING, "File not found: {0}", file.getAbsolutePath());
            try {
                logger.log(Level.INFO, "Creating file: {0}", file.getAbsolutePath());
                file.createNewFile();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error when creating file", e);
            }
        }
        Properties prop = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream(file));) {
            prop.load(in);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when reading file", e);
        }
        prop.setProperty(user, "10");
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file));) {
            prop.store(out, "User=Frequency");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when writing file", e);
        }
    }

}
