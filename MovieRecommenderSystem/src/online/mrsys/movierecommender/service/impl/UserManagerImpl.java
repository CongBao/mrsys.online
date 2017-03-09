package online.mrsys.movierecommender.service.impl;

import java.util.List;

import online.mrsys.movierecommender.dao.FavoriteDao;
import online.mrsys.movierecommender.dao.MovieDao;
import online.mrsys.movierecommender.dao.RatingDao;
import online.mrsys.movierecommender.dao.RoleDao;
import online.mrsys.movierecommender.dao.UserDao;
import online.mrsys.movierecommender.domain.Favorite;
import online.mrsys.movierecommender.domain.Role;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;
import online.mrsys.movierecommender.vo.RoleBean;
import online.mrsys.movierecommender.vo.UserBean;

public class UserManagerImpl implements UserManager {
	
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
	public int validLogin(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int validRegister(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addFavorite(Favorite favorite) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserExist(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User updateAccount(User origin, String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updatePassword(User origin, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateEmail(User origin, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateMailVerifyState(User origin, boolean verified) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateRole(User origin, int roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role getRoleByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBean getUserBeanByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleBean getRoleBeanByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Favorite> getFavoritesByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFavorite(Favorite favorite) {
		// TODO Auto-generated method stub
		
	}

}
