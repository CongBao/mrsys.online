package online.mrsys.movierecommender.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import online.mrsys.movierecommender.service.MovieManager;

public class RecommendJob extends QuartzJobBean {
	
	private boolean isRunning = false;
	
	private MovieManager movieManager;

	public void setMovieManager(MovieManager movieManager) {
		this.movieManager = movieManager;
	}

	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		// TODO auto schedule movie recommender via movie manager
		
	}

}
