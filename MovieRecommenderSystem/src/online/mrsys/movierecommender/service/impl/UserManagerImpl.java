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
import online.mrsys.movierecommender.function.PasswordValidator;
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
	public int validLogin(User user) throws Exception {
		final User toValid = user;
		toValid.setPassword(PasswordValidator.calculate(user.getPassword(), user.getAccount()));
		List<User> users = userDao.findByAccountAndPass(toValid);
		if (users.size() >= 1) {
			if (users.get(0).getRole().getId() == ADMIN) {
				return LOGIN_ADMIN;
			} else if (users.get(0).getRole().getId() == USER) {
				return LOGIN_USER;
			}
		}
		return LOGIN_FAIL;
	}

	@Override
	public int validRegister(User user) throws Exception {
		final User toRegister = user;
		toRegister.setPassword(PasswordValidator.calculate(user.getPassword(), user.getAccount()));
		if (!isUserExist(toRegister)) {
			if (toRegister.getRole().getId() == ADMIN) {
				if (userDao.save(toRegister) != null) {
					return REGISTER_ADMIN;
				}
			} else if (toRegister.getRole().getId() == USER) {
				if (userDao.save(toRegister) != null) {
					return REGISTER_USER;
				}
			}
		}
		return REGISTER_FAIL;
	}

	@Override
	public boolean addFavorite(Favorite favorite) {
		if (!isFavoriteExist(favorite)) {
			if (favoriteDao.save(favorite) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isUserExist(User user) {
		if (userDao.findByAccount(user.getAccount()) != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isFavoriteExist(Favorite favorite) {
		if (favoriteDao.findById(favorite.getId()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public User updateAccount(User origin, String account) throws Exception {
		if (!isUserExist(origin)) {
			final User change = origin;
			change.setAccount(account);
			change.setPassword(PasswordValidator.calculate(origin.getPassword(), account));
			userDao.update(change);
			return change;
		}
		return null;
	}

	@Override
	public User updatePassword(User origin, String password) throws Exception {
		final User change = origin;
		change.setPassword(PasswordValidator.calculate(password, origin.getAccount()));
		userDao.update(change);
		return change;
	}

	@Override
	public User updateEmail(User origin, String email) {
		final User change = origin;
		change.setEmail(email);
		userDao.update(change);
		return change;
	}

	@Override
	public User updateMailVerifyState(User origin, boolean verified) {
		final User change = origin;
		change.setMailVerified(verified);
		userDao.update(change);
		return change;
	}

	@Override
	public User updateRole(User origin, int roleId) {
		final User change = origin;
		final Role role = roleDao.findById(roleId);
		if (role != null) {
			change.setRole(role);
			userDao.update(change);
			return change;
		}
		return null;
	}

	@Override
	public User getUserByAccount(String account) {
		return userDao.findByAccount(account);
	}

	@Override
	public Role getRoleByAccount(String account) {
		final User user = getUserByAccount(account);
		if (user != null) {
			return user.getRole();
		}
		return null;
	}

	@Override
	public UserBean getUserBeanByAccount(String account) {
		final User user = getUserByAccount(account);
		if (user != null) {
			return new UserBean(user.getId(), user.getAccount(), user.getEmail(), user.isMailVerified(), getRoleBeanByAccount(user.getAccount()));
		}
		return null;
	}

	@Override
	public RoleBean getRoleBeanByAccount(String account) {
		final Role role = getRoleByAccount(account);
		if (role != null) {
			return new RoleBean(role.getId(), role.getRole());
		}
		return null;
	}

	@Override
	public List<Favorite> getFavoritesByUser(User user) {
		return favoriteDao.findByUser(user);
	}

	@Override
	public boolean deleteUser(User user) {
		if (isUserExist(user)) {
			userDao.delete(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteFavorite(Favorite favorite) {
		if (isFavoriteExist(favorite)) {
			favoriteDao.delete(favorite);
			return true;
		}
		return false;
	}

}
