package com.huhuo.integration.base;

import java.util.List;
import java.util.Map;

import com.huhuo.integration.exception.DaoException;

/**
 * basic operation for DB
 *@author wuyuxuan
 * @param <T>
 */
public interface IBaseDao<T> {

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
	 * delete records logically by batch
	 * @see #delete(Object)
	 */
	<PK> Integer deleteBatch(List<PK> ids) throws DaoException;
	/**
	 * delete model by id, physical delete
	 * @param t
	 * @return
	 * @throws DaoException
	 */
	Integer deletePhysical(T t) throws DaoException;
	/**
	 * delete records physically by batch
	 * @see #deletePhysical(Object)
	 */
	<PK> Integer deletePhysicalBatch(List<PK> ids) throws DaoException;
	/**
	 * delete model by id
	 * @param id
	 * @return the total number of affected row
	 * @throws DaoException
	 */
	<PK> Integer deleteById(PK id) throws DaoException;
	/**
	 * Query given SQL to create a prepared statement from SQL and a
	 * list of arguments to bind to the query, expecting a result list.
	 * <p>The results will be mapped to a List (one entry for each row) of
	 * Maps (one entry for each column, using the column name as the key).
	 * Each element in the list will be of the form returned by this interface's
	 * queryForMap() methods.
	 * @param sql SQL query to execute
	 * @param args arguments to bind to the query
	 * (leaving it to the PreparedStatement to guess the corresponding SQL type);
	 * @return a List that contains a Map per row
	 * @throws DaoException if the query fails
	 */
	List<Map<String, Object>> queryForMapList(String sql, Object... args) throws DaoException;
	/**
	 * Query given SQL to create a prepared statement from SQL and a
	 * list of arguments to bind to the query, expecting a result Map.
	 * The queryForMap() methods defined by this interface are appropriate
	 * when you don't have a domain model. Otherwise, consider using
	 * one of the queryForObject() methods.
	 * <p>The query is expected to be a single row query; the result row will be
	 * mapped to a Map (one entry for each column, using the column name as the key).
	 * @param sql SQL query to execute
	 * @param args arguments to bind to the query
	 * (leaving it to the PreparedStatement to guess the corresponding SQL type);
	 * @return the result Map (one entry for each column, using the, null if the query does not
	 * column name as the key)
	 * return exactly one row
	 * @throws DaoException if the query fails
	 */
	Map<String, Object> queryForMap(String sql, Object... args) throws DaoException;
	/**
	 * execute the sql, and retrieve a list with element mapped by @param clazz
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	<E> List<E> queryForList(String sql, Class<E> clazz, Object... args) throws DaoException;
	/**
	 * execute the sql, and retrieve an single object mapped by @param clazz
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return null if no available result was expected
	 * @throws DaoException
	 */
	<E> E queryForObject(String sql, Class<E> clazz, Object... args) throws DaoException;
	/**
	 * get the total number
	 * @return
	 */
	Long count();
	/**
	 * Issue a single SQL update operation (such as an insert, update or delete statement)
	 * via a prepared statement, binding the given arguments.
	 * @param sql SQL containing bind parameters
	 * @param args arguments to bind to the query
	 * (leaving it to the PreparedStatement to guess the corresponding SQL type);
	 * @return the number of rows affected
	 * @throws DaoException if there is any problem issuing the update
	 */
	int update(String sql, Object... args) throws DaoException;
	/**
	 * Issue multiple SQL updates on a single JDBC Statement using batching.
	 * <p>Will fall back to separate updates on a single Statement if the JDBC
	 * driver does not support batch updates.
	 * @param sql defining an array of SQL statements that will be executed.
	 * @return an array of the number of rows affected by each statement
	 * @throws DaoException if there is any problem executing the batch
	 */
	int[] batchUpdate(String[] sql) throws DaoException;
	/**
	 * Issue a single SQL execute, typically a DDL statement.
	 * @param sql static SQL to execute
	 * @throws DaoException if there is any problem
	 */
	void execute(String sql) throws DaoException;
}
