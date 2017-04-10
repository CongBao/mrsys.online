package online.mrsys.movierecommender.schedule;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import online.mrsys.movierecommender.service.UserManager;

public class RecommendJob extends QuartzJobBean {
	
    private static final Logger logger = Logger.getLogger(RecommendJob.class.getName());
    
	private boolean isRunning = false;
	
	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		if (!isRunning) {
		    isRunning = true;
		    logger.log(Level.INFO, "Start recommending job");
		    try {
                userManager.recommendMovies();
            } catch (MqttException e) {
                logger.log(Level.SEVERE, "Fail to create mqtt client", e);
            } catch (FileNotFoundException e) {
                logger.log(Level.SEVERE, "File not found", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, null, e);
            }
		    logger.log(Level.INFO, "Recommending job completed, next time: ", ctx.getNextFireTime());
		    isRunning = false;
		}
	}

}
