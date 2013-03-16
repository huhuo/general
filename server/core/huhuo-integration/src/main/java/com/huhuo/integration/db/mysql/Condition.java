package com.huhuo.integration.db.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Condition<T> {

	/**
	 * 查询实体
	 */
	protected T t;
	/**
	 * 备用查询包装对象
	 */
	protected Map<String, Object> opt;
	/**
	 * 分页
	 */
	protected Page page;
	/**
	 * 排序（多字段）
	 */
	protected List<Order> orderList;
	/**
	 * 分组
	 */
	protected List<Group> groupList;
	
	private String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	public Condition() {
		super();
	}
	
	public Condition(T t, List<Group> groupList, List<Order> orderList, Page page) {
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

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
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
