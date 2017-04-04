package online.mrsys.movierecommender.service.impl;

import java.util.List;

import online.mrsys.movierecommender.dao.FavoriteDao;
import online.mrsys.movierecommender.dao.MovieDao;
import online.mrsys.movierecommender.dao.RatingDao;
import online.mrsys.movierecommender.dao.RoleDao;
import online.mrsys.movierecommender.dao.UserDao;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.Rating;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.MovieManager;
import online.mrsys.movierecommender.vo.MovieBean;

public class MovieManagerImpl implements MovieManager {

	private MovieDao movieDao;
	private RatingDao ratingDao;
	@SuppressWarnings("unused")
    private FavoriteDao favoriteDao;
	@SuppressWarnings("unused")
    private RoleDao roleDao;
	@SuppressWarnings("unused")
    private UserDao userDao;

	public void setMovieDao(MovieDao movieDao) {
		this.movieDao = movieDao;
	}

	public void setRatingDao(RatingDao ratingDao) {
		this.ratingDao = ratingDao;
	}
	
	public void setFavoriteDao(FavoriteDao favoriteDao) {
	    this.favoriteDao = favoriteDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean addMovie(Movie movie) {
		if (!isMovieExist(movie)) {
			if (movieDao.save(movie) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addRating(Rating rating) {
		if (!isRatingExist(rating)) {
			if (ratingDao.save(rating) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isMovieExist(Movie movie) {
		if (movieDao.findByImdb(movie.getImdb()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isRatingExist(Rating rating) {
		if (ratingDao.findById(rating.getId()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public Movie updateYear(Movie origin, int year) {
		final Movie change = origin;
		change.setYear(year);
		movieDao.update(change);
		return change;
	}

	@Override
	public Movie updateTitle(Movie origin, String title) {
		final Movie change = origin;
		change.setTitle(title);
		movieDao.update(change);
		return change;
	}

	@Override
	public Rating updateRating(Rating origin, float rating) {
		final Rating change = origin;
		change.setRating(rating);
		ratingDao.update(change);
		return change;
	}

	@Override
	public Movie getMovieById(int id) {
		return movieDao.findById(id);
	}

	@Override
	public MovieBean getMovieBeanById(int id) {
		final Movie movie = getMovieById(id);
		if (movie != null) {
			return new MovieBean(movie.getId(), movie.getImdb(), movie.getYear(), movie.getTitle());
		}
		return null;
	}

	@Override
	public Movie getMovieByImdb(int imdb) {
		return movieDao.findByImdb(imdb);
	}

	@Override
	public MovieBean getMovieBeanByImdb(int imdb) {
		final Movie movie = movieDao.findByImdb(imdb);
		if (movie != null) {
			return new MovieBean(movie.getId(), movie.getImdb(), movie.getYear(), movie.getTitle());
		}
		return null;
	}

	@Override
	public List<Movie> getMoviesByYear(int year) {
		return movieDao.findByYear(year);
	}

	@Override
	public List<Movie> getMoviesByTitle(String title) {
		return movieDao.findByTitle(title);
	}

	@Override
	public List<Rating> getRatingsByUser(User user) {
		return ratingDao.findByUser(user);
	}

	@Override
	public List<Rating> getRatingsByMovie(Movie movie) {
		return ratingDao.findByMovie(movie);
	}

	@Override
	public boolean deleteMovie(Movie movie) {
		if (isMovieExist(movie)) {
			movieDao.delete(movie);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteRating(Rating rating) {
		if (isRatingExist(rating)) {
			ratingDao.delete(rating);
			return true;
		}
		return false;
	}

}
