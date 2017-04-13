package online.mrsys.movierecommender.action.movie;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.vo.MovieBean;

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
            return SUCCESS;
        }
        actionContext.getSession().put(WebConstant.INTERCEPT, "Movie does not exist");
        return ERROR;
    }
    
}
