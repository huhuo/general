package com.eray.systemmanage.dictionary;

import java.io.Serializable;

import com.huhuo.integration.base.BaseModel;

public class ModelDictionary extends BaseModel implements Serializable {

	private static final long serialVersionUID = 5758441409692939318L;
	private String groupName;
	private String groupDisplayName;
	private String dictKey;
	private String dictValue;
	private String dictDisplayName;
	private Integer orderNo;
	private String comment;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDisplayName() {
		return groupDisplayName;
	}
	public void setGroupDisplayName(String groupDisplayName) {
		this.groupDisplayName = groupDisplayName;
	}
	public String getDictKey() {
		return dictKey;
	}
	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public String getDictDisplayName() {
		return dictDisplayName;
	}
	public void setDictDisplayName(String dictDisplayName) {
		this.dictDisplayName = dictDisplayName;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
