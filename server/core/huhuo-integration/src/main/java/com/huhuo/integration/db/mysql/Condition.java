package com.huhuo.integration.db.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Condition<T> {

	/** query entity which field will be used to construct WHERE clause if it isn't null */
	protected T t;
	/** reserve object to construct complicated custom WHERE clause */
	protected Map<String, Object> opt = new HashMap<String, Object>();
	/** additional where clause **/
	protected List<Where> whereList;
	/** page object to construct LIMIT clause */
	protected Page<T> page;
	/** ORDER clause */
	protected List<Order> orderList;
	/** GROUP clause */
	protected List<Group> groupList;
	
	private String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	public Condition() {
		super();
	}
	
	public Condition(T t, List<Group> groupList, List<Order> orderList, Page<T> page) {
		super();
		this.t = t;
		this.groupList = groupList;
		this.orderList = orderList;
		this.page = page;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public Map<String, Object> getOpt() {
		return opt;
	}

	public void setOpt(Map<String, Object> opt) {
		this.opt = opt;
	}

	public List<Where> getWhereList() {
		return whereList;
	}

	public void setWhereList(List<Where> whereList) {
		this.whereList = whereList;
	}
	
	public void setWhereList(Where... wheres) {
		List<Where> list = new ArrayList<Where>();
		for(Where where : wheres) {
			list.add(where);
		}
		setWhereList(list);
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	
	public void setOrderList(Order... orders) {
		List<Order> list = new ArrayList<Order>();
		for(Order order : orders) {
			list.add(order);
		}
		setOrderList(list);
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
	
	public void setGroupList(Group... groups) {
		List<Group> list  = new ArrayList<Group>();
		for(Group group : groups) {
			list.add(group);
		}
		setGroupList(list);
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONStringWithDateFormat(this, dateFormat);
	}

}
