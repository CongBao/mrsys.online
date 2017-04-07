package online.mrsys.movierecommender.action.user;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.Rating;

public class RateMovieAction extends BaseAction {
    
    private Rating rating;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public String execute() throws Exception {
        // TODO Auto-generated method stub
        return super.execute();
    }
    
}
