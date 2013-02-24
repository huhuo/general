package com.eray.systemmanage.province;

import java.io.Serializable;

import com.huhuo.integration.base.BaseModel;

public class ModelProvince extends BaseModel implements Serializable {
	private static final long serialVersionUID = -7320899332947769228L;
	private String name;
	private String spelling;
	private Integer orderNo;
	private String mapAreaId;
	private Boolean hasMetaData;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getMapAreaId() {
		return mapAreaId;
	}

	public void setMapAreaId(String mapAreaId) {
		this.mapAreaId = mapAreaId;
	}

	public Boolean getHasMetaData() {
		return hasMetaData;
	}

	public void setHasMetaData(Boolean hasMetaData) {
		this.hasMetaData = hasMetaData;
	}

	public static final Long ALL_PROVINCE_ID = 0L;
	public static final String ALL_PROVINCE_NAME = "全部";

	public static final Long NO_PROVINCE_ID = -1L;
	public static final String NO_PROVINCE_NAME = "无";
}
