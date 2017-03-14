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
	 * Find a list of favorite records by movie.
	 * 
	 * @param movie
	 *            the movie to be queried
	 * @return a list of favorite records found
	 */
	List<Favorite> findByMovie(Movie movie);

}
