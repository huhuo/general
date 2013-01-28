package com.huhuo.integration.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huhuo.integration.db.mysql.Condition;


/**
 * 通用条件查询接口
 * @author wuyuxuan
 * @param <T>
 */
public interface IBaseExtenseMapper<T> extends IBaseMapper<T> {
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int addBatch(@Param("list") List<T> list);
	/**
	 * 条件选择，如果参数为空，不作为选择条件
	 * @param condition
	 * @return 选择记录
	 */
	List<T> findByCondition(Condition<T> condition);
	/**
	 * 获取符合条件的记录总数目
	 * @param condition
	 * @return
	 */
	Long countByCondition(Condition<T> condition);
}
