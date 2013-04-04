package com.huhuo.integration.db.mysql;

import java.util.List;

public class Page<T> {

	/**
	 * 起始记录
	 */
	private Integer start = 0;
	/**
	 * 分页数量pageSize
	 */
	private Integer limit = 10;
	/**
	 * 分页众数
	 */
	private Long total = 0l;

	/**
	 * 分頁號碼
	 */
	private Integer pageNo = 1;

	/**
	 * 分页数据
	 */
	private List<T> records;

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
		if(start!=null){
			this.start = start;
			// 满足分页取数据
			if (start != null && this.limit != null && start % this.limit == 0) {
				this.pageNo = start / this.limit+1;
			}
		}
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if(limit!=null){
			this.limit = limit;
		}
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		if (pageNo != null) {

			this.pageNo = pageNo;
			if (pageNo != null && limit != null) {
				this.start = (pageNo-1)  * this.limit;
			}

		}
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		if(total!=null){
			this.total = total;
		}
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "Page [start=" + start + ", limit=" + limit + ", total=" + total
				+ ", pageNo=" + pageNo + ", records=" + records + "]";
	}

}
