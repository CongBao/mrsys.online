package online.mrsys.movierecommender.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import online.mrsys.movierecommender.service.UserManager;

public class RecommendJob extends QuartzJobBean {
	
	private boolean isRunning = false;
	
	private UserManager userManager;

	public void setMovieManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		// TODO auto schedule movie recommender via movie manager
		
	}

}
