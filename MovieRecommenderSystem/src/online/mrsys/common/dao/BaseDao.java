package online.mrsys.common.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {

	/**
	 * Load an entity via id.
	 * 
	 * @param entityClazz
	 *            the class of entity
	 * @param id
	 *            a serializable entity instance contains id
	 * @return the entity got
	 */
	T get(Class<T> entityClazz, Serializable id);

	/**
	 * Save an entity to database.
	 * 
	 * @param entity
	 *            the entity to save
	 * @return the entity saved
	 */
	Serializable save(T entity);

	/**
	 * Update an entity record.
	 * 
	 * @param entity
	 *            the entity to update
	 */
	void update(T entity);

	/**
	 * Delete an entity record.
	 * 
	 * @param entity
	 *            the entity to delete
	 */
	void delete(T entity);

	/**
	 * Delete en entity via id
	 * 
	 * @param entityClazz
	 *            the class of entity
	 * @param id
	 *            a serializable entity instance contains id
	 */
	void delete(Class<T> entityClazz, Serializable id);

	/**
	 * Obtain all entities.
	 * 
	 * @param entityClazz
	 *            the class of entity
	 * @return a list of all entities obtained
	 */
	List<T> findAll(Class<T> entityClazz);

	/**
	 * Obtain the total number of entity.
	 * 
	 * @param entityClazz
	 *            the class of entity
	 * @return the total number of entity
	 */
	long findCount(Class<T> entityClazz);

}
