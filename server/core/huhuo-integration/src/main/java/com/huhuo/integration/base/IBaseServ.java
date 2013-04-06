package com.huhuo.integration.base;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huhuo.integration.exception.DaoException;

public interface IBaseServ <T> {
	/**
	 * Return the JdbcTemplate for this DAO,
	 * pre-initialized with the DataSource or set explicitly.
	 * @return
	 */
	JdbcTemplate getJdbcTemplate();
	
	/**
	 * persist a new record to DB
	 * @param t
	 * @return rows count affected, null if t == null
	 */
	Integer add(T t) throws DaoException;
	/**
	 * find object by id, with no internal Object injected
	 * @param <V>
	 * @param id
	 * @return
	 */
	<V> T find(V id) throws DaoException;
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
	 * update DB object t
	 * @param t
	 * @return rows count affected, null if t == null
	 */
	Integer update(T t) throws DaoException;
	/**
	 * 获取数据库表的总记录数
	 * @return
	 */
	Long count() throws DaoException;
}