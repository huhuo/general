package com.huhuo.integration.db.mysql;

public class Page {

	/**
	 * 起始记录
	 */
	private Integer start;
	/**
	 * 分页数量pageSize
	 */
	private Integer limit;

	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Page(Integer start, Integer limit) {
		super();
		this.start = start;
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "Page [start=" + start + ", limit=" + limit + "]";
	}

}
