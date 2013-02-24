package com.huhuo.integration.base;

import java.util.List;

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
	<PK> T find(Class<T> clazz, PK id) throws DaoException;
	/**
	 * get model by page, return all if start==null and limit==null
	 * @param mappedClass
	 * @param start
	 * @param limit
	 * @return
	 * @throws DaoException
	 */
	List<T> findModels(Class<T> mappedClass, Integer start, Integer limit) throws DaoException;
	/**
	 * find by condition
	 * @param condition
	 * @return
	 */
	List<T> findByCondition(Condition<T> condition);
	/**
	 * count by condition
	 * @param condition
	 * @return
	 */
	Long countByCondition(Condition<T> condition);
}
