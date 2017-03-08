package online.mrsys.movierecommender.dao;

import java.util.List;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.Favorite;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.User;

public interface FavoriteDao extends BaseDao<Favorite> {
	
	List<Favorite> findByUser(User user);
	
	List<Favorite> findByMovie(Movie movie);

}
