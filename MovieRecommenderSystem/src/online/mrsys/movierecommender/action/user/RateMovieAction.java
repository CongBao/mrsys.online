package online.mrsys.movierecommender.action.user;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Rating;

public class RateMovieAction extends BaseAction {

    private static final long serialVersionUID = 1924260166254928590L;
    
    private Rating rating;

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	@Override
	public String execute() throws Exception {
		ActionContext actionContext = ActionContext.getContext();
		if (movieManager.addRating(getRating())) {
			return SUCCESS;
		}
		actionContext.getSession().put(WebConstant.INTERCEPT, "Rating exists");
		return ERROR;
	}

}
