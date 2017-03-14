package online.mrsys.movierecommender.dao;

import online.mrsys.common.dao.BaseDao;
import online.mrsys.movierecommender.domain.Role;

public interface RoleDao extends BaseDao<Role> {

	/**
	 * Find a role record by id.
	 * 
	 * @param id
	 *            the id of role
	 * @return the role record found
	 */
	Role findById(Integer id);

}
