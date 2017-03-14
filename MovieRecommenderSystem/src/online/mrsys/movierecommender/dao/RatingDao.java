package online.mrsys.movierecommender.dao;

import java.util.List;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.domain.User;

public interface RatingDao extends BaseDao<Rating> {

	/**
	 * Find a list of rating records by user.
	 * 
	 * @param user
	 *            the user to be queried
	 * @return a list of rating records found
	 */
	List<Rating> findByUser(User user);

	/**
	 * Find a list of rating records by movie.
	 * 
	 * @param movie
	 *            the movie to be queried
	 * @return a list of rating records found
	 */
	List<Rating> findByMovie(Movie movie);

}
