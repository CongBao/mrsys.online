package online.mrsys.movierecommender.action.admin;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Movie;

public class AddMovieAction extends BaseAction {
    
    private static final long serialVersionUID = -31125094003686109L;
    
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        if (movieManager.addMovie(getMovie())) {
            return SUCCESS;
        }
        actionContext.getSession().put(WebConstant.INTERCEPT, "Movie exists");
        return ERROR;
    }
    
}
