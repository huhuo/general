package com.huhuo.integration.db.mysql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Where implements Serializable {

	private static final long serialVersionUID = -6508819240479660604L;

	/** independent where clause without AND and OR **/
	private String sql;
	/** parameters for where clause **/
	private List<Object> params;
	/** join to connect other where clause **/
	private Join join = Join.AND;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<Object> getParams() {
		return params;
	}
	public void setParams(List<Object> params) {
		this.params = params;
	}
	public Join getJoin() {
		return join;
	}
	public void setJoin(Join join) {
		this.join = join;
	}
	public Where(String sql, List<Object> params) {
		super();
		this.sql = sql;
		this.params = params;
	}
	public Where(String sql, Object... params) {
		super();
		this.sql = sql;
		List<Object> list = new ArrayList<Object>();
		for(Object param : params) {
			list.add(param);
		}
		this.params = list;
	}
	public Where(String sql, Join join, Object... params) {
		super();
		this.join = join;
		this.sql = sql;
		List<Object> list = new ArrayList<Object>();
		for(Object param : params) {
			list.add(param);
		}
		this.params = list;
	}
	
	public enum Join {
		AND,
		OR
		;
	}
}
