package online.mrsys.movierecommender.dao;

import java.util.List;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.domain.User;

public interface RatingDao extends BaseDao<Rating> {
	
	List<Rating> findByUser(User user);
	
	List<Rating> findByMovie(Movie movie);

}
