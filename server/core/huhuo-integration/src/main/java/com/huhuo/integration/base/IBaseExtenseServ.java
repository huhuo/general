package com.huhuo.integration.base;

import java.util.List;

import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;


/**
 * 通用条件查询接口
 * @author wuyuxuan
 * @param <T>
 */
public interface IBaseExtenseServ<T> extends IBaseServ<T> {
	/**
	 * 批量插入
	 * @param list
	 * @return rows number affected
	 */
	Integer addBatch(List<T> list);
	/**
	 * query by criteria, which have relationship with and
	 * @param condition
	 * @return an array list of record, or empty object if there is no record match
	 */
	List<T> findByCondition(Condition<T> condition);
	/**
	 * 是否需要注入关联对象
	 * @param condition
	 * @param injected 是否注入关联对象
	 * @return
	 */
	List<T> findByCondition(Condition<T> condition, boolean injected);
	/**
	 * 获取符合条件的记录总数目
	 * @param condition
	 * @return
	 */
	Long countByCondition(Condition<T> condition);
	/**
	 * get all record by page
	 * @param page
	 * @return
	 */
	List<T> findModels(Page<T> page);
}
