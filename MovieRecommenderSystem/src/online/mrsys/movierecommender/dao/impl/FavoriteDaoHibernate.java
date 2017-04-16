package online.mrsys.movierecommender.dao.impl;

import java.util.List;

import online.mrsys.common.dao.impl.BaseDaoHibernate;
import online.mrsys.movierecommender.dao.FavoriteDao;
import online.mrsys.movierecommender.domain.Favorite;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.User;

public class FavoriteDaoHibernate extends BaseDaoHibernate<Favorite> implements FavoriteDao {
	
	@Override
	public Favorite findById(Integer id) {
		List<Favorite> favorites = find("select f from Favorite f where f.id = ?0", id);
		if (favorites != null && favorites.size() >= 1)
			return favorites.get(0);
		return null;
	}

	@Override
	public List<Favorite> findByUser(User user) {
		return find("select f from Favorite f where f.userId = ?0", user.getId());
	}
	
    @Override
    public List<Favorite> findByUser(Integer userId) {
        return find("select f from Favorite f where f.userId = ?0", userId);
    }

	@Override
	public List<Favorite> findByMovie(Movie movie) {
		return find("select f from Favorite f where f.movieId = ?0", movie.getId());
	}
	
    @Override
    public List<Favorite> findByMovie(Integer movieId) {
        return find("select f from Favorite f where f.movieId = ?0", movieId);
    }

    @Override
    public Favorite findByUserAndMovie(User user, Movie movie) {
        List<Favorite> favorites = find("select f from Favorite f where f.userId = ?0 and f.movieId = ?1", user.getId(), movie.getId());
        if (favorites != null && favorites.size() >= 1) {
            return favorites.get(0);
        }
        return null;
    }

    @Override
    public Favorite findByUserAndMovie(Integer userId, Integer movieId) {
        List<Favorite> favorites = find("select f from Favorite f where f.userId = ?0 and f.movieId = ?1", userId, movieId);
        if (favorites != null && favorites.size() >= 1) {
            return favorites.get(0);
        }
        return null;
    }

}
