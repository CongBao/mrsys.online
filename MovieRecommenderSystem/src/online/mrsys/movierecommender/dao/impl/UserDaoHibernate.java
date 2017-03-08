package online.mrsys.movierecommender.dao.impl;

import java.util.List;

import online.mrsys.common.dao.impl.BaseDaoHibernate;
import online.mrsys.movierecommender.dao.UserDao;
import online.mrsys.movierecommender.domain.User;

public class UserDaoHibernate extends BaseDaoHibernate<User> implements UserDao {

	@Override
	public List<User> findByAccountAndPass(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

}
