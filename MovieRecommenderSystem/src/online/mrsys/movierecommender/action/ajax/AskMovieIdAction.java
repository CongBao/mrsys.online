package online.mrsys.movierecommender.action.ajax;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.Movie;

public class AskMovieIdAction extends BaseAction {

    private static final long serialVersionUID = -4605317763278495214L;

    private Integer id;
    
    private Movie movie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String execute() throws Exception {
        Movie movie = movieManager.getMovieByImdb(getMovie().getImdb());
        if (movie != null) {
            setId(movie.getId());
        } else {
            movieManager.addMovie(getMovie());
            movie = movieManager.getMovieByImdb(getMovie().getImdb());
            setId(movie.getId());
        }
        return SUCCESS;
    }
    
}
