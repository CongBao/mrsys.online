package online.mrsys.movierecommender.dao;

import java.util.List;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.Movie;

public interface MovieDao extends BaseDao<Movie> {
	
	Movie findById(Integer id);
	
	List<Movie> findByYear(Integer year);
	
	List<Movie> findByTitle(String title);

}
