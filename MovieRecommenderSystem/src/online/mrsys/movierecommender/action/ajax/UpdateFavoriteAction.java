package online.mrsys.movierecommender.action.ajax;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Favorite;
import online.mrsys.movierecommender.vo.MovieBean;
import online.mrsys.movierecommender.vo.UserBean;

public class UpdateFavoriteAction extends BaseAction {

    private static final long serialVersionUID = 8776254560643269004L;
    
    private static final String LOGIN = "login";
    private static final String FAIL = "fail";
    private static final String ADDED = "added";
    private static final String REMOVED = "removed";

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        UserBean userBean = (UserBean) actionContext.getSession().get(WebConstant.USER);
        MovieBean movieBean = (MovieBean) actionContext.getSession().get(WebConstant.MOVIE);
        if (userBean == null) {
            setStatus(LOGIN);
        }
        if (movieBean == null) {
            setStatus(FAIL);
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(userBean.getId());
        favorite.setMovieId(movieBean.getId());
        if (userManager.addFavorite(favorite)) {
            setStatus(ADDED);
        } else {
            favorite = userManager.getFavoriteByUserAndMovie(userBean.getId(), movieBean.getId());
            userManager.deleteFavorite(userBean.getId(), movieBean.getId());
            setStatus(REMOVED);
        }
        return SUCCESS;
    }
    
}
