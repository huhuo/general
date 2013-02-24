package com.eray.systemmanage.security.user;

import java.io.Serializable;
import java.util.Date;

import com.huhuo.integration.base.BaseModel;

public class ModelUser extends BaseModel implements Serializable {

	private static final long serialVersionUID = 6849543151755358241L;
	private String username;
	private String displayName;
	private String password;
	private String contactPhone;
	private String cellphone;
	private String email;
	private Long departmentId;
	private Long provinceId;
	private String remark;
	private Date createTime;
	private Date pwdExpireTime;
	private Boolean activeStatus;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPwdExpireTime() {
		return pwdExpireTime;
	}

	public void setPwdExpireTime(Date pwdExpireTime) {
		this.pwdExpireTime = pwdExpireTime;
	}
	
	public Boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public static final String ADMIN = "admin";

}
