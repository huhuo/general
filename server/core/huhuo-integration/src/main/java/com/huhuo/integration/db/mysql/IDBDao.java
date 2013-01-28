package com.huhuo.integration.db.mysql;

import java.util.List;

import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.exception.DaoException;

/**
 * basic operation for DB
 *@author wuyuxuan
 * @param <T>
 */
public interface IDBDao<T> {

	/**
	 * persist T's instance of this interface by it's primary key id, if not exist in DB, then add a new record
	 * @param t
	 * @throws DaoException 
	 */
	boolean save(T t) throws DaoException;
	/**
	 * insert a new record, and auto assign the generated key value to id of @param t
	 * @param t
	 * @return null if t==null, else the generated key value
	 * @throws DaoException
	 */
	Long insert(T t) throws DaoException;
	/**
	 * update model t by id
	 * @param t
	 * @return null if t==null, else the generated key value
	 * @throws DaoException
	 */
	Integer update(T t) throws DaoException;
	/**
	 * delete model by id
	 * @param t
	 * @return if t==null return null, else the total number of affected row
	 * @throws DaoException
	 */
	Integer delete(T t) throws DaoException;
	/**
	 * delete model by id
	 * @param id
	 * @return the total number of affected row
	 * @throws DaoException
	 */
	<PK> Integer deleteById(PK id) throws DaoException;
	/**
	 * execute the sql, and retrieve a list with element mapped by @param clazz
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	List<T> queryForList(String sql, Class<T> clazz, Object... args) throws DaoException;
	/**
	 * execute the sql, and retrieve an single object mapped by @param clazz
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return null if no available result was expected
	 * @throws DaoException
	 */
	T queryForObject(String sql, Class<T> clazz, Object... args) throws DaoException;
	/**
	 * get model by id
	 * @param clazz
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	<PK> T findById(Class<T> clazz, PK id) throws DaoException;
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
}
