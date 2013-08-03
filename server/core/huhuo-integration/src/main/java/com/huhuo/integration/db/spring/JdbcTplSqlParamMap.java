package com.huhuo.integration.db.spring;

import java.util.HashMap;
import java.util.Map;

/**
 * Create sql StringBuilder and parameter Map. Is used in {@link JdbcTplUtils}
 * method's parameter and bring result of the method.
 * @author wuyuxuan
 * 
 */
public class JdbcTplSqlParamMap {
	public Map<String, Object> paramMap;
	public StringBuilder sql;
	
	public JdbcTplSqlParamMap(){
		paramMap = new HashMap<String, Object>();
		sql = new StringBuilder();
	}
	
	public JdbcTplSqlParamMap(StringBuilder sql, Map<String, Object> paramMap){
		this.sql = sql;
		this.paramMap = paramMap;
	}
}
