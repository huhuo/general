package com.huhuo.integration.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.config.GlobalConstant;

public class BaseModel implements IBaseModel<Long> {
	
	private static final long serialVersionUID = 6332922078575691255L;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * primary key specified with name "id" and type "Long"
	 */
	protected Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
