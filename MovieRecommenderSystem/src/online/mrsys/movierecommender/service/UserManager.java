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

    int ADMIN = 1;
    int USER = 2;

    /**
     * Check if a login request if valid.
     * 
     * @param user
     *            the user to be checked
     * @return a flag of result, where
     *         <ul>
     *         <li>LOGIN_FAIL = -1</li>
     *         <li>LOGIN_USER = 0</li>
     *         <li>LOGIN_ADMIN = 1</li>
     *         </ul>
     */
    int validLogin(User user) throws Exception;

    /**
     * Check if a register request is valid.
     * 
     * @param user
     *            the user to be checked
     * @return a flag of result, where
     *         <ul>
     *         <li>REGISTER_FAIL = -1</li>
     *         <li>REGISTER_USER = 0</li>
     *         <li>REGISTER_ADMIN = 1</li>
     *         </ul>
     */
    int validRegister(User user) throws Exception;

    /**
     * Add a favorite record to database.
     * 
     * @param favorite
     *            the favorite record to be added
     * @return if the operation succeeds
     */
    boolean addFavorite(Favorite favorite);

    /**
     * Check if a user is existing in database. The check is based on user's
     * account.
     * 
     * @param user
     *            the user to be checked
     * @return if the user is existing in database
     */
    boolean isUserExist(User user);

    /**
     * Check if a user is existing in database. The check is based on user's
     * email.
     * 
     * @param email
     *            the email of user to be checked
     * @return if the user is existing in database
     */
    boolean isUserExist(String email);

    /**
     * Check if a favorite record is existing in database. The check is based on
     * favorite id.
     * 
     * @param favorite
     *            the favorite record to be checked
     * @return if the favorite record is existing in database
     */
    boolean isFavoriteExist(Favorite favorite);

    /**
     * Update the account record of a user.
     * 
     * @param origin
     *            the user to be updated
     * @param account
     *            the new account of user
     * @return the user record after updating
     */
    User updateAccount(User origin, String account) throws Exception;

    /**
     * Update the password record of a user.
     * 
     * @param origin
     *            the user to be updated
     * @param password
     *            the new password of user
     * @return the user record after updating
     */
    User updatePassword(User origin, String password) throws Exception;

    /**
     * Update the email record of a user.
     * 
     * @param origin
     *            the user to be updated
     * @param email
     *            the new email of user
     * @return the user record after updating
     */
    User updateEmail(User origin, String email);

    /**
     * Update the state of mail verification of a user.
     * 
     * @param origin
     *            the user to be updated
     * @param verified
     *            the new mail verifies state of user
     * @return the user record after updating
     */
    User updateMailVerifyState(User origin, boolean verified);

    /**
     * Update the recommendation list of user.
     * 
     * @param origin
     *            the user to be updated
     * @param recommend
     *            the new recommended list
     * @return the user record after updating
     */
    User updateRecommendation(User origin, byte[] recommend);

    /**
     * Update the role of a user.
     * 
     * @param origin
     *            the user to be updated
     * @param roleId
     *            the new role id of user
     * @return the user record after updating
     */
    User updateRole(User origin, int roleId);

    /**
     * Obtain a user record by the user's id.
     * 
     * @param id
     *            the id of user
     * @return the user record obtained
     */
    User getUserById(int id);

    /**
     * Obtain a user record by the user's account.
     * 
     * @param account
     *            the account of user
     * @return the user record obtained
     */
    User getUserByAccount(String account);

    /**
     * Obtain a role record by the user's account.
     * 
     * @param account
     *            the account of user
     * @return the role record obtained
     */
    Role getRoleByAccount(String account);

    /**
     * Obtain a user bean instance by the user's account.
     * 
     * @param account
     *            the account of user
     * @return the user bean instance obtained
     */
    UserBean getUserBeanByAccount(String account, MovieManager movieManager);

    /**
     * Obtain a role bean instance by the user's account.
     * 
     * @param account
     *            the account of user
     * @return the role bean instance obtained
     */
    RoleBean getRoleBeanByAccount(String account);

    /**
     * Obtain a list of favorite records of a user.
     * 
     * @param user
     *            the user to be queried
     * @return a list of favorite records obtained
     */
    List<Favorite> getFavoritesByUser(User user);

    /**
     * Delete a user record in database.
     * 
     * @param user
     *            the user to be deleted
     * @return if the delete is successful
     */
    boolean deleteUser(User user);

    /**
     * Delete a favorite record in database.
     * 
     * @param favorite
     *            the favorite record to be deleted
     * @return if the delete is successful
     */
    boolean deleteFavorite(Favorite favorite);

    /**
     * Recommend a list of movies to users.
     */
    void recommendMovies() throws Exception;

}
