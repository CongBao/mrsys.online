package online.mrsys.movierecommender.action.base;

import com.opensymphony.xwork2.ActionSupport;

import online.mrsys.movierecommender.service.MovieManager;
import online.mrsys.movierecommender.service.UserManager;

public class UserBaseAction extends ActionSupport {
	
	private static final long serialVersionUID = -3649398033369233265L;
	
	protected UserManager userManager;
	protected MovieManager movieManager;
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public void setMovieManager(MovieManager movieManager) {
		this.movieManager = movieManager;
	}

}
