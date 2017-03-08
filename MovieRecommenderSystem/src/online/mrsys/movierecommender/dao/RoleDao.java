package online.mrsys.movierecommender.dao;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.Role;

public interface RoleDao extends BaseDao<Role> {
	
	Role findById(Integer id);

}
