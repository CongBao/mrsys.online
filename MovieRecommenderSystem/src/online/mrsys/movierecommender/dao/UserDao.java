package online.mrsys.movierecommender.dao;

import java.util.List;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.User;

public interface UserDao extends BaseDao<User> {
	
	List<User> findByAccountAndPass(User user);
	
	User findByAccount(String account);

}
