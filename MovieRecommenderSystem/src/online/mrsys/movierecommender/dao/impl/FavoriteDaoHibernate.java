package online.mrsys.movierecommender.dao.impl;

import java.util.List;

import online.mrsys.common.dao.impl.BaseDaoHibernate;
import online.mrsys.movierecommender.dao.FavoriteDao;
import online.mrsys.movierecommender.domain.Favorite;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.User;

public class FavoriteDaoHibernate extends BaseDaoHibernate<Favorite> implements FavoriteDao {

	@Override
	public List<Favorite> findByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Favorite> findByMovie(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

}
