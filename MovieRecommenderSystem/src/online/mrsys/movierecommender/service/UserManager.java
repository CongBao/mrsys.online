package online.mrsys.movierecommender.service;

import java.util.List;

import online.mrsys.movierecommender.domain.Favorite;
import online.mrsys.movierecommender.domain.Role;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.vo.RoleBean;
import online.mrsys.movierecommender.vo.UserBean;

public interface UserManager {
	
	int LOGIN_FAIL = -1;
	int LOGIN_USER = 0;
	int LOGIN_ADMIN = 1;
	
	int REGISTER_FAIL = -1;
	int REGISTER_USER = 0;
	int REGISTER_ADMIN = 1;

	int validLogin(User user);
	
	int validRegister(User user);
	
	boolean addFavorite(Favorite favorite);
	
	boolean isUserExist(User user);
	
	User updateAccount(User origin, String account);
	
	User updatePassword(User origin, String password);
	
	User updateEmail(User origin, String email);
	
	User updateMailVerifyState(User origin, boolean verified);
	
	User updateRole(User origin, int roleId);
	
	User getUserByAccount(String account);
	
	Role getRoleByAccount(String account);
	
	UserBean getUserBeanByAccount(String account);
	
	RoleBean getRoleBeanByAccount(String account);
	
	List<Favorite> getFavoritesByUser(User user);
	
	void deleteUser(User user);
	
	void deleteFavorite(Favorite favorite);
	
}
