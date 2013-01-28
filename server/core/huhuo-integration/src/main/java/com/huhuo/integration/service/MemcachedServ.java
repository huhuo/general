package com.huhuo.integration.service;

import net.rubyeye.xmemcached.MemcachedClient;

public interface MemcachedServ {

	/**
	 * key不压缩
	 * @see #get(String, String, boolean, long)
	 */
	<T> T get(String region, final String key);
	/**
	 * key不压缩
	 * @see #get(String, String, boolean, long)
	 */
	<T> T get(String region, String key, long timeout);
	/**
	 * @see #get(String, String, boolean, long)
	 */
	<T> T get(String region, final String key, boolean isCompressKey);

	/**
	 * 获取memcached的数据
	 * 
	 * @param region
	 * @param key
	 * @param isCompressKey
	 *            true:压缩key的长度为32; false:不压缩
	 * @param timeout
	 *            操作超时,单位是毫秒
	 * @return
	 */
	<T> T get(String region, String key, boolean isCompressKey, long timeout);
	/**
	 * key不压缩
	 * @see #set(String, String, Object, boolean, int, long)
	 */
	boolean set(String region, String key, Object value);
	/**
	 * @see #set(String, String, Object, boolean, int, long)
	 */
	boolean set(String region, String key, Object value, boolean isCompressKey);
	/**
	 * key不压缩
	 * @see #set(String, String, Object, boolean, int, long)
	 */
	boolean set(String region, String key, Object value, int exp, long timeout);
	/**
	 * 设置memcached的键值对
	 * 
	 * @param region
	 *            存储区
	 * @param key
	 * @param value
	 * @param isCompressKey
	 *            true:压缩key的长度为32; false:不压缩
	 * @param exp
	 *            过期时间，秒
	 * @param timeout
	 *            操作超时时间，毫秒
	 * @return
	 */
	boolean set(String region, String key, Object value, boolean isCompressKey, int exp, long timeout);
	/**
	 * 删除缓存
	 * @param region
	 * @param key
	 * @return
	 */
	boolean delete(String region, final String key);
	/**
	 * 删除缓存
	 * @param region
	 * @param key
	 * @return
	 */
	boolean delete(String region, final String key, boolean isCompressKey);
	/**
	 * 令memcached中的所有数据失效
	 */
	void flushAll();
	
	/**
	 * 获取memcached的状态，true：打开；false：关闭
	 * @return
	 */
	boolean getIsOn();
	/**
	 * 设置memcached的状态，true：打开；false：关闭
	 */
	void setIsOn(boolean isOn);
	/**
	 * 获取memcachedClient
	 * @return
	 */
	MemcachedClient getMemcachedClient();
	/**
	 * 设置memcachedClient
	 * @param memcachedClient
	 */
	void setMemcachedClient(MemcachedClient memcachedClient);
	/**
	 * 获取默认操作超时时间
	 */
	long getDefaultTimeout();
	/**
	 * 设置默认操作超时时间
	 * @param timeout
	 */
	void setDefaultTimeout(long timeout);
	/**
	 * 获取默认过期时间
	 * @return
	 */
	int getDefaultExpiration();
	/**
	 * 设置默认过期时间
	 * @param exp
	 */
	void setDefalutExpiration(int exp);
}
