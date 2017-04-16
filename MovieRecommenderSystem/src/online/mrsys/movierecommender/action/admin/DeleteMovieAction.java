package online.mrsys.movierecommender.action.admin;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Movie;

public class DeleteMovieAction extends BaseAction {
    
    private static final long serialVersionUID = -863968185438174742L;
    
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        Movie movie = movieManager.getMovieById(getId());
        if (movie == null) {
            actionContext.getSession().put(WebConstant.INTERCEPT_2, "Movie does not exist");
            return ERROR;
        }
        movieManager.deleteMovie(getId());
        actionContext.getSession().put(WebConstant.INTERCEPT_2, "Success");
        return SUCCESS;
    }
    
}
