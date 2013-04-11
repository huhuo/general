package com.huhuo.integration.base;

import java.io.Serializable;
import java.util.Date;

public interface IBaseModel<PK> extends Serializable {

	/** primary key attribute **/
	void setId(PK id);
	PK getId();

	/** special field for deleting logically **/
	Integer getStatus();
	void setStatus(Integer status);
	
	/** create time **/
	public Date getCreateTime();
	public void setCreateTime(Date createTime);

	/** update time **/
	public Date getUpdateTime();
	public void setUpdateTime(Date updateTime);
}
