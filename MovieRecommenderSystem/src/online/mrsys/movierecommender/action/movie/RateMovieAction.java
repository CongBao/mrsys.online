package online.mrsys.movierecommender.action.movie;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.vo.MovieBean;
import online.mrsys.movierecommender.vo.UserBean;

public class RateMovieAction extends BaseAction {
    
    private static final long serialVersionUID = 7610454161768935589L;
    
    private Rating rating;

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	@Override
	public String execute() throws Exception {// TODO unstable
		ActionContext actionContext = ActionContext.getContext();
		Rating rating = getRating();
		rating.setUser(userManager.getUserById(((UserBean) actionContext.getSession().get(WebConstant.USER)).getId()));
		rating.setMovie(movieManager.getMovieById(((MovieBean) actionContext.getSession().get(WebConstant.MOVIE)).getId()));
		if (movieManager.addRating(rating)) {
			return SUCCESS;
		}
		actionContext.getSession().put(WebConstant.INTERCEPT, "Rating exists");
		return ERROR;
	}

}
