package com.huhuo.integration.db.mysql;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.config.GlobalConstant;

public class Page<T> {

	/**
	 * 起始记录
	 */
	private Long start = 0L;
	/**
	 * 分页数量pageSize
	 */
	private Long limit = 10L;
	/**
	 * 分页众数
	 */
	private Long total = 0l;

	/**
	 * 分頁號碼
	 */
	private Long pageNo = 1L;

	/**
	 * 分页数据
	 */
	private List<T> records;

	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Page(Long start, Long limit) {
		super();
		this.start = start;
		this.limit = limit;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		if(start!=null){
			this.start = start;
			// 满足分页取数据
			if (start != null && this.limit != null && start % this.limit == 0) {
				this.pageNo = start / this.limit+1;
			}
		}
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		if(limit!=null){
			this.limit = limit;
		}
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
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
		return JSONObject.toJSONStringWithDateFormat(this,
				GlobalConstant.DateFormat.LONG_FORMAT);
	}


	

}
