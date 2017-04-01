package online.mrsys.movierecommender.dao.impl;

import java.util.List;

import online.mrsys.common.dao.impl.BaseDaoHibernate;
import online.mrsys.movierecommender.dao.RatingDao;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.domain.User;

public class RatingDaoHibernate extends BaseDaoHibernate<Rating> implements RatingDao {

	@Override
	public List<Rating> findByUser(User user) {
		return find("select r from Rating r where r.user_id=?0", user.getId());
	}

	@Override
	public List<Rating> findByMovie(Movie movie) {
		return find("select r from Rating r where r.movie_id=?0", movie.getId());
	}

}
