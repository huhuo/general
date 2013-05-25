package com.huhuo.integration.db.mysql;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.config.GlobalConstant.DateFormat;

public class Dict implements Serializable {

	private static final long serialVersionUID = -307383429798382760L;

	/** key to identify a unique Dict **/
	private Integer key;
	/** explain text for specified Dict **/
	private String disp;
	
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getDisp() {
		return disp;
	}
	public void setDisp(String disp) {
		this.disp = disp;
	}
	
	public Dict(String group, Integer key, String disp) {
		super();
		this.key = key;
		this.disp = disp;
		DictMgr.add(group, this);
	}
	
	@Override
	public String toString() {
		return JSONObject.toJSONStringWithDateFormat(this, DateFormat.LONG_FORMAT);
	}

	public String toString(String dateFormat) {
		return JSONObject.toJSONStringWithDateFormat(this, dateFormat);
	}
	
}
