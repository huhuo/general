package com.huhuo.integration.db.spring;

import java.util.Map;
/**
 * 
 * @author wuyuxuan
 */
public interface IJdbcTplCustomCondition {
	/**
	 * Build special condition by opt to result.
	 * 
	 * @param opt
	 * @param result
	 * @return true: build; false: not.
	 */
	boolean buildWhere(Map<String, Object> opt, JdbcTplSqlParamMap result);
}
