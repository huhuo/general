package com.huhuo.integration.base;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huhuo.integration.exception.DaoException;

/**
 * basic operation for DB
 *@author wuyuxuan
 * @param <T>
 */
public interface IBaseDao<T> {

	/**
	 * Return the JdbcTemplate for this DAO,
	 * pre-initialized with the DataSource or set explicitly.
	 * @return
	 */
	JdbcTemplate getJdbcTemplate();
	/**
	 * insert a new record, and auto assign the generated key value to id of @param t
	 * @param t
	 * @return rows number affected
	 * @throws DaoException
	 */
	Integer add(T t) throws DaoException;
	/**
	 * update model t by id
	 * @param t
	 * @return null if t==null, else the generated key value
	 * @throws DaoException
	 */
	Integer update(T t) throws DaoException;
	/**
	 * persist T's instance of this interface by it's primary key id, if not exist in DB, then add a new record
	 * @param t
	 * @throws DaoException 
	 */
	boolean save(T t) throws DaoException;
	/**
	 * delete a record logically, just setting status field in t to be 0
	 * @param t
	 * @return if t==null return null, else the total number of affected row
	 * @throws DaoException
	 */
	Integer delete(T t) throws DaoException;
	/**
	 * delete model by id, physical delete
	 * @param t
	 * @return
	 * @throws DaoException
	 */
	Integer deletePhysical(T t) throws DaoException;
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
	 * @see #queryForList(String, Class, Object...)
	 */
	List<T> queryForList(String sql, Object... args) throws DaoException;
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
	 * @see #queryForList(String, Class, Object...)
	 */
	T queryForObject(String sql, Object... args) throws DaoException;
	/**
	 * get the total number
	 * @return
	 */
	Long count();
}
