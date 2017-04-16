package online.mrsys.common.util;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextFinalizer implements ServletContextListener {
    
    private static final Logger logger = Logger.getLogger(ContextFinalizer.class.getName());

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Class<?> cls = Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
            Method mth = cls == null ? null : cls.getMethod("shutdown");
            if (mth != null) {
                logger.log(Level.INFO, "MySQL connection cleanup thread shutdown");
                mth.invoke(null);
                logger.log(Level.INFO, "MySQL connection cleanup thread shutdown succeeds");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to shutdown SQL connection cleanup thread", e);
        }
        
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == cl) {
                try {
                    logger.log(Level.INFO, "Deregistering JDBC driver {0}", driver);
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error deregistering JDBC driver " + driver, e);
                }
            } else {
                logger.log(Level.INFO, "Not deregistering JDBC driver {0} as it does not belong to this webapp's ClassLoader", driver);
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

}
