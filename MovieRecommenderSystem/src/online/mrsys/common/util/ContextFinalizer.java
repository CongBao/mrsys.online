package online.mrsys.common.util;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextFinalizer implements ServletContextListener {
    
    private static final Logger logger = Logger.getLogger(ContextFinalizer.class.getName());
    
    private class ThreadInfo {
        
        private final String name;
        private final String cause;
        private final String stop;
        
        public ThreadInfo(String name, String cause, String stop) {
            this.name = name;
            this.cause = cause;
            this.stop = stop;
        }

        public String getName() {
            return name;
        }

        public String getCause() {
            return cause;
        }

        public String getStop() {
            return stop;
        }
        
    }
    
    private List<ThreadInfo> threads = Arrays.asList(
            new ThreadInfo("com.mysql.jdbc.AbandonedConnectionCleanupThread",
                           "Abandoned connection cleanup thread",
                           "shutdown")
            );

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == cl) {
                try {
                    DriverManager.deregisterDriver(driver);
                    logger.log(Level.INFO, "Driver {0} deregistered", driver);
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Fail to deregister driver " + driver, e);
                }
            } else {
                logger.log(Level.INFO, "Not deregister driver {0} as it does not belong to this webapp's ClassLoader", driver);
            }
        }
        
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArr = threadSet.toArray(new Thread[threadSet.size()]);
        for (Thread thread : threadArr) {
            for (ThreadInfo info: threads) {
                if (thread.getName().contains(info.getCause())) {
                    synchronized (thread) {
                        try {
                            Class<?> cls = Class.forName(info.getName());
                            if (cls != null) {
                                Method mth = cls.getMethod(info.getStop());
                                if (mth != null) {
                                    mth.invoke(null);
                                    logger.log(Level.INFO, "Connection cleanup thread {0} shutdown successfully", info.getName());
                                }
                            }
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Failed to shutdown connection cleanup thread: " + info.getName(), e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

}
