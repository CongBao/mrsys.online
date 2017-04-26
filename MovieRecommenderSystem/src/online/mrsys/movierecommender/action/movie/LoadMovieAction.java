package online.mrsys.movierecommender.action.movie;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.vo.FavoriteBean;
import online.mrsys.movierecommender.vo.MovieBean;
import online.mrsys.movierecommender.vo.RatingBean;
import online.mrsys.movierecommender.vo.UserBean;

public class LoadMovieAction extends BaseAction {

    private static final long serialVersionUID = -7930707141756071386L;
    
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        MovieBean movieBean = movieManager.getMovieBeanById(Integer.parseInt(getId()));
        if (movieBean != null) {
            actionContext.getSession().put(WebConstant.MOVIE, movieBean);
            UserBean userBean = (UserBean) actionContext.getSession().get(WebConstant.USER);
            // favorite by the current signed in user
            if (userBean != null) {
                FavoriteBean favoriteBean = userManager.getFavoriteBeanByUserBeanAndMovieBean(userBean, movieBean);
                if (favoriteBean != null) {
                    actionContext.getSession().put(WebConstant.FAVORITE, favoriteBean);
                } else {
                    actionContext.getSession().put(WebConstant.FAVORITE, null);
                }
            }
            // ratings by the current signed in user
            if (userBean != null) {
                RatingBean ratingBean = movieManager.getRatingBeanByUserBeanAndMovieBean(userBean, movieBean, userManager);
                if (ratingBean != null) {
                    actionContext.getSession().put(WebConstant.RATING, ratingBean);
                } else {
                    actionContext.getSession().put(WebConstant.RATING, null);
                }
            }
            return SUCCESS;
        }
        actionContext.getSession().put(WebConstant.INTERCEPT, "Movie does not exist");
        return ERROR;
    }
    
}
