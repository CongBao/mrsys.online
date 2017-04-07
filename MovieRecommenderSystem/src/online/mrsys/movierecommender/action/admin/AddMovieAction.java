package online.mrsys.movierecommender.action.admin;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.Movie;

public class AddMovieAction extends BaseAction {
    
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String execute() throws Exception {
        // TODO Auto-generated method stub
        return super.execute();
    }
    
}
