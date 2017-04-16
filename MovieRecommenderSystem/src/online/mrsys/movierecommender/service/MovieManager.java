package online.mrsys.movierecommender.service;

import java.util.List;

import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.vo.MovieBean;
import online.mrsys.movierecommender.vo.RatingBean;
import online.mrsys.movierecommender.vo.UserBean;

public interface MovieManager {

    /**
     * Add a movie record to database.
     * 
     * @param movie
     *            the movie record to be added
     * @return if the operation succeeds
     */
    boolean addMovie(Movie movie);

    /**
     * Add a rating record to database.
     * 
     * @param rating
     *            the rating record to be added
     * @return if the operation succeeds
     */
    boolean addRating(Rating rating);

    /**
     * Check if a movie is existing in database. The check is based on imdb.
     * 
     * @param movie
     *            the movie to be checked
     * @return if the movie is existing in database
     */
    boolean isMovieExist(Movie movie);

    /**
     * Check if a rating record is existing in database.
     * 
     * @param rating
     *            the rating record to be checked
     * @return if the rating record is existing in database
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
    Movie updateYear(Movie origin, int year) throws Exception;

    /**
     * Update the title record of a movie.
     * 
     * @param origin
     *            the movie to be updated
     * @param title
     *            the new title of movie
     * @return the movie record after updating
     */
    Movie updateTitle(Movie origin, String title) throws Exception;

    /**
     * Update the rating value of a rating record.
     * 
     * @param origin
     *            the rating record to be updated
     * @param rating
     *            the new rating value of the rating record
     * @return the rating record after updating
     */
    Rating updateRating(Rating origin, float rating) throws Exception;

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
     * Obtain a movie record by the movie's imdb id.
     * 
     * @param imdb
     *            the imdb id of movie
     * @return the movie record obtained
     */
    Movie getMovieByImdb(String imdb);

    /**
     * Obtain a movie bean instance by the movie's imdb id
     * 
     * @param imdb
     *            the imdb id of movie
     * @return the movie bean instance obtained
     */
    MovieBean getMovieBeanByImdb(String imdb);

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
     * Obtain a rating record by user and movie
     * 
     * @param user
     *            the user of this rating
     * @param movie
     *            the movie of this rating
     * @return the rating record obtained
     */
    Rating getRatingByUserAndMovie(User user, Movie movie);

    /**
     * Obtain a rating bean instance by user bean and movie bean.
     * 
     * @param userBean
     *            the user bean instance of this rating
     * @param movieBean
     *            the movie bean instance of this rating
     * @param userManager
     *            the instance of user manager
     * @return the rating bean instance obtained
     */
    RatingBean getRatingBeanByUserBeanAndMovieBean(UserBean userBean, MovieBean movieBean, UserManager userManager);

    /**
     * Obtain the total number of movie.
     * 
     * @return the number of movies in database
     */
    long getMovieCount();

    /**
     * Delete a movie record in database
     * 
     * @param id
     *            the id of movie record to be deleted
     */
    void deleteMovie(Integer id);

    /**
     * Delete a rating record in database.
     * 
     * @param rating
     *            the rating record to be deleted
     * @return if the delete is successful
     */
    boolean deleteRating(Rating rating);

}
