package com.huhuo.integration.base;

import java.util.List;

import com.huhuo.integration.exception.DaoException;

public interface IBaseDB<T> {
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
	Boolean save(T t) throws DaoException;
	/**
	 * delete a record logically, just setting status field in t to be 0
	 * @param t
	 * @return if t==null return null, else the total number of affected row
	 * @throws DaoException
	 */
	Integer delete(T t) throws DaoException;
	/**
	 * delete records logically by batch
	 * @see #delete(Object)
	 */
	<PK> Integer deleteBatch(List<PK> ids) throws DaoException;
	/**
	 * get the total number
	 * @return
	 */
	Long count();
}
