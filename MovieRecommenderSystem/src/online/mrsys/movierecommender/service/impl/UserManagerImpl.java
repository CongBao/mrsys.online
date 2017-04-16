package online.mrsys.movierecommender.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.MqttException;

import online.mrsys.movierecommender.dao.FavoriteDao;
import online.mrsys.movierecommender.dao.MovieDao;
import online.mrsys.movierecommender.dao.RatingDao;
import online.mrsys.movierecommender.dao.RoleDao;
import online.mrsys.movierecommender.dao.UserDao;
import online.mrsys.movierecommender.domain.Favorite;
import online.mrsys.movierecommender.domain.Role;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.MovieManager;
import online.mrsys.movierecommender.service.UserManager;
import online.mrsys.movierecommender.util.MovieRecommender;
import online.mrsys.movierecommender.util.PasswordValidator;
import online.mrsys.movierecommender.vo.FavoriteBean;
import online.mrsys.movierecommender.vo.MovieBean;
import online.mrsys.movierecommender.vo.RoleBean;
import online.mrsys.movierecommender.vo.UserBean;

public class UserManagerImpl implements UserManager {

    private FavoriteDao favoriteDao;
    private UserDao userDao;
    @SuppressWarnings("unused")
    private RoleDao roleDao;
    @SuppressWarnings("unused")
    private MovieDao movieDao;
    @SuppressWarnings("unused")
    private RatingDao ratingDao;

    public void setFavoriteDao(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public void setRatingDao(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public int validLogin(User user) throws Exception {
        final User toValid = new User();
        toValid.setAccount(user.getAccount());
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
        final User toRegister = (User) user.clone();
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
    public boolean isUserExist(String email) {
        if (userDao.findByEmail(email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isFavoriteExist(Favorite favorite) {
        if (favoriteDao.findByUserAndMovie(favorite.getUserId(), favorite.getMovieId()) != null) {
            return true;
        }
        return false;
    }

    @Override
    public User updateAccount(User origin, String account) throws Exception {
        if (!isUserExist(origin)) {
            final User change = (User) origin.clone();
            change.setAccount(account);
            change.setPassword(PasswordValidator.calculate(origin.getPassword(), account));
            userDao.update(change);
            return change;
        }
        return null;
    }

    @Override
    public User updatePassword(User origin, String password) throws Exception {
        final User change = (User) origin.clone();
        change.setPassword(PasswordValidator.calculate(password, origin.getAccount()));
        userDao.update(change);
        return change;
    }

    @Override
    public User updateEmail(User origin, String email) throws Exception {
        final User change = (User) origin.clone();
        change.setEmail(email);
        userDao.update(change);
        return change;
    }

    @Override
    public User updateMailVerifyState(User origin, boolean verified) throws Exception {
        final User change = (User) origin.clone();
        change.setMailVerified(verified);
        userDao.update(change);
        return change;
    }
    
    @Override
    public User updateRecommendation(User origin, byte[] recommend) throws Exception {
        final User change = (User) origin.clone();
        change.setRecommendation(recommend);
        userDao.update(change);
        return change;
    }

    @Override
    public User updateRole(User origin, Role role) throws Exception {
        final User change = (User) origin.clone();
        change.setRole(role);
        userDao.update(change);
        return change;
    }
    
    @Override
    public User getUserById(int id) {
        return userDao.findById(id);
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
            UserBean userBean = new UserBean();
            userBean.setId(user.getId());
            userBean.setAccount(user.getAccount());
            userBean.setEmail(user.getEmail());
            userBean.setMailVerified(user.getMailVerified());
            userBean.setRole(getRoleBeanByAccount(account));
            return userBean;
        }
        return null;
    }

    @Override
    public RoleBean getRoleBeanByAccount(String account) {
        final Role role = getRoleByAccount(account);
        if (role != null) {
            if (role.getId() == ADMIN) {
                return RoleBean.ADMIN;
            } else if (role.getId() == USER) {
                return RoleBean.USER;
            }
        }
        return null;
    }

    @Override
    public List<Favorite> getFavoritesByUser(User user) {
        return favoriteDao.findByUser(user);
    }
    
    @Override
    public List<Favorite> getFavoritesByUser(Integer userId) {
        return favoriteDao.findByUser(userId);
    }
    
    @Override
    public List<FavoriteBean> getFavoriteBeansByUserBean(UserBean userBean, MovieManager movieManager) {
        List<FavoriteBean> favoriteBeans = new ArrayList<>();
        List<Favorite> favorites = getFavoritesByUser(userBean.getId());
        favorites.forEach(item -> {
            FavoriteBean favoriteBean = new FavoriteBean();
            favoriteBean.setUser(userBean);
            favoriteBean.setMovie(movieManager.getMovieBeanById(item.getMovieId()));
            favoriteBeans.add(favoriteBean);
        });
        return favoriteBeans;
    }
    
    @Override
    public Favorite getFavoriteByUserAndMovie(Integer userId, Integer movieId) {
        return favoriteDao.findByUserAndMovie(userId, movieId);
    }
    
    @Override
    public FavoriteBean getFavoriteBeanByUserBeanAndMovieBean(UserBean userBean, MovieBean movieBean) {
        Favorite favorite = getFavoriteByUserAndMovie(userBean.getId(), movieBean.getId());
        if (favorite != null) {
            FavoriteBean favoriteBean = new FavoriteBean();
            favoriteBean.setUser(userBean);
            favoriteBean.setMovie(movieBean);
            return favoriteBean;
        }
        return null;
    }
    
    @Override
    public long getUserCount() {
        return userDao.findCount(User.class);
    }

    @Override
    public void deleteUser(Integer id) {
        userDao.delete(User.class, id);
    }

    @Override
    public void deleteFavorite(Integer userId, Integer movieId) {
        favoriteDao.delete(getFavoriteByUserAndMovie(userId, movieId));
    }

    @Override
    public void recommendMovies() throws MqttException, FileNotFoundException {
        final File file = new File("/tmp/mrsys.online/user.buf");
        if (!file.exists()) {
            throw new FileNotFoundException("Cannot find file " + file.getAbsolutePath());
        }
        Properties prop = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream(file));) {
            prop.load(in);
        } catch (IOException e) {
        }
        List<User> users = new ArrayList<>();
        prop.stringPropertyNames().forEach(key -> {
            User user = getUserById(Integer.parseInt(key));
            if (user != null) {
                users.add(user);
            }
        });
        MovieRecommender recommender = new MovieRecommender(this);
        recommender.recommend(users);
    }

}
