package online.mrsys.movierecommender.dao;

import java.util.List;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.Favorite;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.User;

public interface FavoriteDao extends BaseDao<Favorite> {

    /**
     * Find a list of favorite records by user.
     * 
     * @param user
     *            the user to be queried
     * @return a list of favorite records found
     */
    List<Favorite> findByUser(User user);

    /**
     * Find a list of favorite records by user id.
     * 
     * @param userId
     *            the user id to be queried
     * @return a list of favorite records found
     */
    List<Favorite> findByUser(Integer userId);

    /**
     * Find a list of favorite records by movie.
     * 
     * @param movie
     *            the movie to be queried
     * @return a list of favorite records found
     */
    List<Favorite> findByMovie(Movie movie);

    /**
     * Find a list of favorite records by movie id.
     * 
     * @param movieId
     *            the movie id to be queried
     * @return a list of favorite records found
     */
    List<Favorite> findByMovie(Integer movieId);

    /**
     * Find a favorite record by user and movie.
     * 
     * @param user
     *            the user of this favorite
     * @param movie
     *            the movie of this favorite
     * @return the favorite record found
     */
    Favorite findByUserAndMovie(User user, Movie movie);

    /**
     * Find a favorite record by user id and movie id.
     * 
     * @param userId
     *            the user id of this favorite
     * @param movieId
     *            the movie id of this favorite
     * @return the favorite record found
     */
    Favorite findByUserAndMovie(Integer userId, Integer movieId);

}
