package com.huhuo.integration.base;

import java.util.List;
import java.util.Map;

import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.exception.DaoException;

/**
 * basic operation for DB
 *@author wuyuxuan
 * @param <T>
 */
public interface IBaseExtenseDao<T> extends IBaseDao<T>{

	/**
	 * add batch
	 * @param list
	 * @return
	 */
	int[] addBatch(List<T> list);
	/**
	 * get model by id
	 * @param clazz
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	<PK> T find(Class<T> clazz, PK id);
	/**
	 * @see #find(Class, Object)
	 */
	<PK> T find(PK id);
	/**
	 * find model list by ids
	 * @param ids
	 * @return
	 */
	<PK> List<T> findByIds(List<PK> ids);
	/**
	 * @see #queryForList(String, Class, Object...)
	 */
	List<T> findList(String sql, Object... args) throws DaoException;
	/**
	 * @see #queryForList(String, Class, Map)
	 */
	List<T> findList(String sql, Map<String, ?> paramMap) throws DaoException;
	/**
	 * @see #queryForObject(String, Class, Object...)
	 */
	T findObject(String sql, Object... args) throws DaoException;
	/**
	 * get model by page, return all if start==null and limit==null
	 * @param mappedClass
	 * @param start
	 * @param limit
	 * @return
	 * @throws DaoException
	 */
	List<T> findModels(Class<T> mappedClass, Long start, Long limit);
	/**
	 * @see #findModels(Long, Long)
	 */
	List<T> findModels(Long start, Long limit);
	/**
	 * find by condition
	 * @param condition
	 * @return
	 */
	List<T> findByCondition(Condition<T> condition) ;
	/**
	 * count by condition
	 * @param condition
	 * @return
	 */
	Long countByCondition(Condition<T> condition);
}
