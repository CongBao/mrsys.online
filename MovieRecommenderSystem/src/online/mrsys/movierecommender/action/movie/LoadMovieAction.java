package online.mrsys.movierecommender.action.movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Rating;
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
            List<Rating> ratings = movieManager.getRatingsByMovie(movieManager.getMovieById(Integer.parseInt(getId())));
            Map<Long, Integer> ratingMap = new HashMap<>();
            for (Rating rating : ratings) {
                Integer num = ratingMap.get(Math.round((double) rating.getRating()));
                ratingMap.put(Math.round((double) rating.getRating()), num == null ? 0 : num + 1);
            }
            ratingMap.put(-1L, ratings.size());
            actionContext.getSession().put(WebConstant.RATING_MAP, ratingMap);
            return SUCCESS;
        }
        actionContext.getSession().put(WebConstant.INTERCEPT, "Movie does not exist");
        return ERROR;
    }
    
}
