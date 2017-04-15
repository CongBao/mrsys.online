package online.mrsys.movierecommender.action.ajax;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.vo.MovieBean;
import online.mrsys.movierecommender.vo.UserBean;

public class RateMovieAction extends BaseAction {
    
    private static final long serialVersionUID = -6687516218032691340L;

    private boolean valid;
    
    private float rating;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        Rating rating = new Rating();
        rating.setRating(getRating());
        rating.setUser(userManager.getUserById(((UserBean) actionContext.getSession().get(WebConstant.USER)).getId()));
        rating.setMovie(movieManager.getMovieById(((MovieBean) actionContext.getSession().get(WebConstant.MOVIE)).getId()));
        if (movieManager.addRating(rating)) {
            setValid(true);
            return SUCCESS;
        }
        setValid(false);
        return SUCCESS;
    }
    
}
