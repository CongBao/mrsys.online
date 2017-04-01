package online.mrsys.movierecommender.dao.impl;

import java.util.List;

import online.mrsys.common.dao.impl.BaseDaoHibernate;
import online.mrsys.movierecommender.dao.MovieDao;
import online.mrsys.movierecommender.domain.Movie;

public class MovieDaoHibernate extends BaseDaoHibernate<Movie> implements MovieDao {

	@Override
	public Movie findById(Integer id) {
		List<Movie> movies = find("select m from Movie m where m.movie_id = ?0", id);
		if (movies != null && movies.size() >= 1)
			return movies.get(0);
		return null;
	}
	
	@Override
	public Movie findByImdb(Integer imdb) {
		return null;
	}

	@Override
	public List<Movie> findByYear(Integer year) {
		return find("select m from Movie m where m.year = ?0", year);
	}

	@Override
	public List<Movie> findByTitle(String title) {
		return find("select m from Movie m where m.title = ?0", title);
	}

}
