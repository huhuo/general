package com.huhuo.integration.base;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.config.GlobalConstant;

public class BaseModel implements IBaseModel<Long> {
	
	private static final long serialVersionUID = 6332922078575691255L;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** primary key specified with name "id" and type "Long" **/
	protected Long id;
	/** a general mark to indicate the Model's status, it's often used to indicate the instance is logically deleted **/
	protected Integer status;
	/** a general field indicating model's create time or update time **/
	protected Date dateTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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
