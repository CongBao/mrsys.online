package online.mrsys.movierecommender.dao.impl;

import java.util.List;

import online.mrsys.common.dao.impl.BaseDaoHibernate;
import online.mrsys.movierecommender.dao.MovieDao;
import online.mrsys.movierecommender.domain.Movie;

public class MovieDaoHibernate extends BaseDaoHibernate<Movie> implements MovieDao {

	@Override
	public Movie findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Movie findByImdb(Integer imdb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> findByYear(Integer year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> findByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

}
