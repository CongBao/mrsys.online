package online.mrsys.movierecommender.action.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.Rating;

public class LoadRatingsAction extends BaseAction {

    private static final long serialVersionUID = 7025900155646528704L;

    private Map<String, Integer> ratingMap;
    
    private String imdb;

    public Map<String, Integer> getRatingMap() {
        return ratingMap;
    }

    public void setRatingMap(Map<String, Integer> ratingMap) {
        this.ratingMap = ratingMap;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    @Override
    public String execute() throws Exception {
        List<Rating> ratings = movieManager.getRatingsByMovie(movieManager.getMovieByImdb(getImdb()));
        Map<String, Integer> ratingMap = new HashMap<>();
        for (Rating rating : ratings) {
            Integer num = ratingMap.get(String.valueOf(Math.round(rating.getRating())));
            ratingMap.put(String.valueOf(Math.round(rating.getRating())), num == null ? 1 : num + 1);
        }
        ratingMap.put(String.valueOf(-1), ratings.size());
        setRatingMap(ratingMap);
        return SUCCESS;
    }
    
}
