package online.mrsys.movierecommender.dao;

import java.util.List;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.User;

public interface UserDao extends BaseDao<User> {

    /**
     * Find a user record by id.
     * 
     * @param id
     *            the id of user
     * @return the user record found
     */
    User findById(Integer id);

    /**
     * Find a list of users by account and password.
     * 
     * @param user
     *            the user instance contains account and password
     * @return a list of users found
     */
    List<User> findByAccountAndPass(User user);

    /**
     * Find a user record by account.
     * 
     * @param account
     *            the account of user
     * @return the user record found
     */
    User findByAccount(String account);

}
