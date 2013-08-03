package com.huhuo.integration.db.spring;


import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Group;
import com.huhuo.integration.db.mysql.NotSqlField;
import com.huhuo.integration.db.mysql.Order;
import com.huhuo.integration.db.mysql.Page;
import com.huhuo.integration.util.BeanUtils;
import com.huhuo.integration.util.BeanUtils.GetterSetter;
/**
 * util class to build classic SQL clause, such as WHERE, ORDER, GROUP etc
 * @author wuyuxuan
 */
public class JdbcTplUtils {
	public static <T> GetterSetter[] getGetterSetter(final Class<T> beanClazz){
		return BeanUtils.getGetterSetter(beanClazz, NotSqlField.class);
	}
	
	/**
	 * Build select info to sqlResult. Return 'SELECT * FROM tableName'
	 * 
	 * @param tableName
	 * @param sqlResult
	 * @return true:build; false: not
	 */
	public static boolean buildSelect(String tableName, StringBuilder sqlResult){
		if(sqlResult!=null){
			sqlResult.append("SELECT * FROM ").append(tableName);
			return true;
		}
		return false;
	}
	
	public static boolean buildSelectCount(String tableName, StringBuilder sqlResult){
		if(sqlResult!=null){
			sqlResult.append("SELECT count(1) FROM ").append(tableName);
			return true;
		}
		return false;
	}
	
	/**
	 * Build where info to result. Note:<br>
	 * <li>operation is 'AND'
	 * <li>String field use 'fieldName like %value%'
	 * 
	 * @param t
	 * @param result
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return true: build; false: not
	 */
	public static <T> boolean buildWhere(T t,JdbcTplSqlParamMap result) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		boolean r = false;
		if(t!=null && !hasNull(result)){
			boolean first = true;
			GetterSetter[] getterSetterArray = getGetterSetter(t.getClass());
			for(final GetterSetter gs : getterSetterArray){
				Object fieldValue = gs.getter.invoke(t);
				if(fieldValue instanceof String
						|| fieldValue instanceof Integer
						|| fieldValue instanceof Long
						|| fieldValue instanceof Float
						|| fieldValue instanceof Double
						|| fieldValue instanceof Date
						|| fieldValue instanceof Boolean) {
					if(fieldValue!=null){
						if(first){
							result.sql.append(" WHERE ").append(gs.propertyName);
							first = false;
						}else{
							result.sql.append(" AND ").append(gs.propertyName);
						}
						
						if(fieldValue instanceof String) {
							result.sql.append(" LIKE CONCAT('%',:").append(gs.propertyName).append(",'%')");
						} else {
							result.sql.append("=:").append(gs.propertyName);
						}
						result.paramMap.put(gs.propertyName, fieldValue);
						
						r = true;
					}
					
				}
			}
		}
		return r;
	}
	/**
	 * Build groupList info to sqlResult.
	 * 
	 * @param groupList
	 * @param sqlResult groupList info will append sqlResult.
	 * @return true: build; false: not
	 */
	public static boolean buildGroups(List<Group> groupList, StringBuilder sqlResult){
		boolean r = false;
		if(sqlResult!=null  && groupList!=null && groupList.size()>0) {
			sqlResult.append(" GROUP BY ");
			for(Group group : groupList) {
				sqlResult.append(group.getField()).append(" ").append(group.getDir()).append(",");
			}
			sqlResult.deleteCharAt(sqlResult.lastIndexOf(","));
			r = true;
		}
		return r;
	}
	
	
	/**
	 * Build orderList info to sqlResult.
	 * 
	 * @param orderList
	 * @param sqlResult orderList info will append sqlResult.
	 * @return true: build; false: not
	 */
	public static boolean buildOrders(List<Order> orderList, StringBuilder sqlResult){
		boolean r = false;
		if(sqlResult!=null && orderList!=null && orderList.size()>0) {
			sqlResult.append(" ORDER BY ");
			for(Order order : orderList) {
				sqlResult.append(order.getField()).append(" ").append(order.getDir()).append(",");
			}
			sqlResult.deleteCharAt(sqlResult.lastIndexOf(","));
			r = true;
		}
		return r;
	}
	
	/**
	 * {@link #buildOrders(List, StringBuilder)}
	 * 
	 * @param sqlResult
	 * @param orders
	 * @return true: build; false: not
	 */
	public static boolean buildOrders(StringBuilder sqlResult, Order... orders){
		return orders!=null && orders.length>0? buildOrders(Arrays.asList(orders), sqlResult): false;
	}
	
	/**
	 * Build page info to result.
	 * 
	 * @param page
	 * @param result page info will add to result.
	 * @return true: build; false: not
	 */
	@SuppressWarnings("rawtypes")
	public static boolean buildPage(Page page, JdbcTplSqlParamMap result){
		boolean r = false;
		if(page!=null && page.getStart()!=null && page.getLimit()!=null 
				&& !hasNull(result)) {
			result.sql.append(" LIMIT :start,:limit");
			
			result.paramMap.put("start", page.getStart());
			result.paramMap.put("limit", page.getLimit());
			r = true;
		}
		return r;
	}
	
	public static <T> void buildSql(String tableName, Condition<T> condition, IJdbcTplCustomCondition customCondition, JdbcTplSqlParamMap result) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if(hasNull(result)) return;
		
		buildSelect(tableName, result.sql);
		boolean isAdd = buildWhere(condition.getT(), result);
		
		if(customCondition!=null){
			buildCustomWhere(customCondition, condition.getOpt(), result, isAdd);
		}
		
		buildGroups(condition.getGroupList(), result.sql);
		buildOrders(condition.getOrderList(), result.sql);
		buildPage(condition.getPage(), result);
	}
	
	public static boolean buildCustomWhere(IJdbcTplCustomCondition customCondition, Map<String, Object> opt, JdbcTplSqlParamMap result, boolean hasBuildWhere){
		boolean r = false;
		if(!hasNull(result) && opt!=null){
			String addStr = "";
			if(hasBuildWhere){
				addStr = " AND ";
				result.sql.append(addStr);
			}else{
				addStr = " WHERE ";
				result.sql.append(addStr);
			}
			r = opt==null? false: customCondition.buildWhere(opt, result);
			if(!r) result.sql.delete(result.sql.length()-addStr.length(), result.sql.length());
		}
		return r;
	}
	
	public static <T> void buildSqlCount(String tableName, Condition<T> condition, IJdbcTplCustomCondition customCondition, JdbcTplSqlParamMap result) 
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if(hasNull(result)) return;
		
		buildSelectCount(tableName, result.sql);
		boolean isAdd = buildWhere(condition.getT(), result);
		
		if(customCondition!=null){
			buildCustomWhere(customCondition, condition.getOpt(), result, isAdd);
		}
		buildGroups(condition.getGroupList(), result.sql);
	}
	
	public static boolean hasNull(JdbcTplSqlParamMap result){
		return (result==null || result.sql==null || result.paramMap==null)? true: false;
	}
	
}
