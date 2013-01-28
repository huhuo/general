package com.huhuo.integration.base;

public interface IBaseServ <T> {
	/**
	 * 增加对象T
	 * @param t
	 * @return 数据库影响记录条数
	 */
	Integer add(T t);
	/**
	 * 根据id查找
	 * @param <V>
	 * @param id
	 * @return
	 */
	<V> T find(V id);
	/**
	 * 删除对象T
	 * @param t
	 * @return
	 */
	Integer delete(T t);
	/**
	 * 修改对象T
	 * @param t
	 * @return 数据库影响记录条数
	 */
	Integer update(T t);
	/**
	 * 获取数据库表的总记录数
	 * @return
	 */
	Long count();
}