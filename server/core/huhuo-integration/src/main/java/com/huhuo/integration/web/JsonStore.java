package com.huhuo.integration.web;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.config.GlobalConstant;

public class JsonStore<T> {

	private List<T> records;
	
	private Long total;

	public JsonStore(List<T> records, Long total) {
		super();
		this.records = records;
		this.total = total;
	}
	
	public JsonStore(List<T> records, Integer total) {
		super();
		this.records = records;
		if(total != null) {
			this.total = Long.parseLong(total.toString());
		}
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return JSONObject.toJSONStringWithDateFormat(this,
				GlobalConstant.DateFormat.LONG_FORMAT);
	}

	public String toString(String dateFormat) {
		return JSONObject.toJSONStringWithDateFormat(this, dateFormat);
	}

}
