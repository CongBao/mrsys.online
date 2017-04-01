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
	
	private FavoriteDao favoriteDao;
	private MovieDao movieDao;
	private RatingDao ratingDao;
	private RoleDao roleDao;
	private UserDao userDao;

	public void setFavoriteDao(FavoriteDao favoriteDao) {
		this.favoriteDao = favoriteDao;
	}

	public void setMovieDao(MovieDao movieDao) {
		this.movieDao = movieDao;
	}

	public void setRatingDao(RatingDao ratingDao) {
		this.ratingDao = ratingDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean addMovie(Movie movie) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addRating(Rating rating) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMovieExist(Movie movie) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRatingExist(Rating rating) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Movie updateYear(Movie origin, int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie updateTitle(Movie origin, String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rating updateRating(Rating origin, float rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie getMovieById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MovieBean getMovieBeanById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Movie getMovieByImdb(int imdb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MovieBean getMovieBeanByImdb(int imdb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> getMoviesByYear(int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> getMoviesByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Movie> recommendMoviesToUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rating> getRatingsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rating> getRatingsByMovie(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMovie(Movie movie) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRating(Rating rating) {
		// TODO Auto-generated method stub
		
	}

}
