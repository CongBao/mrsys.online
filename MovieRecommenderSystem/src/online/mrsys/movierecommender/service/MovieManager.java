package online.mrsys.movierecommender.service;

import java.util.List;

import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.vo.MovieBean;

public interface MovieManager {
	
	boolean addMovie(Movie movie);
	
	boolean addRating(Rating rating);

	boolean isMovieExist(Movie movie);
	
	boolean isRatingExist(Rating rating);
	
	Movie updateYear(Movie origin, int year);
	
	Movie updateTitle(Movie origin, String title);
	
	Rating updateRating(Rating origin, float rating);
	
	Movie getMovieById(int id);
	
	MovieBean getMovieBeanById(int id);
	
	List<Movie> getMoviesByYear(int year);
	
	List<Movie> getMoviesByTitle(String title);
	
	List<Movie> recommendMoviesToUser(User user); // call *.function.MovieRecommender to process
	
	List<Rating> getRatingsByUser(User user);
	
	List<Rating> getRatingsByMovie(Movie movie);
	
	void deleteMovie(Movie movie);
	
	void deleteRating(Rating rating);
	
}
