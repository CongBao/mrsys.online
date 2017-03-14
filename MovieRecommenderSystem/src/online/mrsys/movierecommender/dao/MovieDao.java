package online.mrsys.movierecommender.dao;

import java.util.List;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.Movie;

public interface MovieDao extends BaseDao<Movie> {

	/**
	 * Find a movie record by id.
	 * 
	 * @param id
	 *            the id of movie
	 * @return the movie record found
	 */
	Movie findById(Integer id);

	/**
	 * Find a list of movie records by year.
	 * 
	 * @param year
	 *            the year of movie
	 * @return a list of movie records found
	 */
	List<Movie> findByYear(Integer year);

	/**
	 * Find a list of movie records by title.
	 * 
	 * @param title
	 *            the title of movie
	 * @return a list of movie records found
	 */
	List<Movie> findByTitle(String title);

}
