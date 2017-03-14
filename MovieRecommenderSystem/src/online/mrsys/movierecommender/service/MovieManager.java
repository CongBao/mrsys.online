package online.mrsys.movierecommender.service;

import java.util.List;

import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.vo.MovieBean;

public interface MovieManager {

	/**
	 * Add a movie record to database.
	 * 
	 * @param movie
	 *            the movie record to be added
	 * @return if the operation succeed
	 */
	boolean addMovie(Movie movie);

	/**
	 * Add a rating record to database.
	 * 
	 * @param rating
	 *            the rating record to be added
	 * @return if the operation succeed
	 */
	boolean addRating(Rating rating);

	/**
	 * Check if a movie is exist in database.
	 * 
	 * @param movie
	 *            the movie to be checked
	 * @return if the movie is exist in database
	 */
	boolean isMovieExist(Movie movie);

	/**
	 * Check if a rating record is exist in database.
	 * 
	 * @param rating
	 *            the rating record to be checked
	 * @return if the rating record is exist in database
	 */
	boolean isRatingExist(Rating rating);

	/**
	 * Update the year record of a movie.
	 * 
	 * @param origin
	 *            the movie to be updated
	 * @param year
	 *            the new year of movie
	 * @return the movie record after updating
	 */
	Movie updateYear(Movie origin, int year);

	/**
	 * Update the title record of a movie.
	 * 
	 * @param origin
	 *            the movie to be updated
	 * @param title
	 *            the new title of movie
	 * @return the movie record after updating
	 */
	Movie updateTitle(Movie origin, String title);

	/**
	 * Update the rating value of a rating record
	 * 
	 * @param origin
	 *            the rating record to be updated
	 * @param rating
	 *            the new rating value of the rating record
	 * @return the rating record after updating
	 */
	Rating updateRating(Rating origin, float rating);

	/**
	 * Obtain a movie record by the movie's id.
	 * 
	 * @param id
	 *            the id of movie
	 * @return the movie record obtained
	 */
	Movie getMovieById(int id);

	/**
	 * Obtain a movie bean instance by the movie's id.
	 * 
	 * @param id
	 *            the id of movie
	 * @return the movie bean instance obtained
	 */
	MovieBean getMovieBeanById(int id);

	/**
	 * Obtain a list of movie records by year.
	 * 
	 * @param year
	 *            the year of movie
	 * @return a list of movie records obtained
	 */
	List<Movie> getMoviesByYear(int year);

	/**
	 * Obtain a list of movie records by title.
	 * 
	 * @param title
	 *            the title of movie
	 * @return a list of movie records obtained
	 */
	List<Movie> getMoviesByTitle(String title);

	/**
	 * Recommend a list of movies to a user.
	 * 
	 * @param user
	 *            the user to be recommended
	 * @return a list of recommended movies
	 */
	List<Movie> recommendMoviesToUser(User user); // call *.function.MovieRecommender to process

	/**
	 * Obtain a list of rating records of a user.
	 * 
	 * @param user
	 *            the user to be queried
	 * @return a list of rating records obtained
	 */
	List<Rating> getRatingsByUser(User user);

	/**
	 * Obtain a list of rating records of a movie.
	 * 
	 * @param movie
	 *            the movie to be queried
	 * @return a list of rating records obtained
	 */
	List<Rating> getRatingsByMovie(Movie movie);

	/**
	 * Delete a movie record in database
	 * 
	 * @param movie
	 *            the movie record to be deleted
	 */
	void deleteMovie(Movie movie);

	/**
	 * Delete a rating record in database.
	 * 
	 * @param rating
	 *            the rating record to be deleted
	 */
	void deleteRating(Rating rating);

}
