package com.eray.systemmanage.city;

import java.io.Serializable;

import com.eray.systemmanage.province.ModelProvince;
import com.huhuo.integration.base.BaseModel;

public class ModelSysCity extends BaseModel implements Serializable{

	private static final long serialVersionUID = -8156066967708458635L;
	private String name;
	private String spelling;
	private Integer orderNo;
	private String zipCode;
	private Integer level;
	private Long provinceId;
	
	private ModelProvince province;

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
		this.spelling = spelling==null?null:spelling.trim();
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode==null?null:zipCode.trim();
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public ModelProvince getProvince() {
		return province;
	}

	public void setProvince(ModelProvince province) {
		this.province = province;
	}
	
	public static final String OPTKEY_PROVINCE_IDS = "provinceIds";
	
	public static final Long ALL_CITY_ID = 0L;
	public static final String ALL_CITY_NAME = "全部";
	
	public static final Long NO_CITY_ID = -1L;
	public static final String NO_CITY_NAME = "无";
}
