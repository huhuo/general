package com.huhuo.integration.base;

import java.io.Serializable;

public interface IBaseModel<PK> extends Serializable {

	/** primary key attribute **/
	void setId(PK id);
	PK getId();

	/** special field for deleting logically **/
	Integer getStatus();
	void setStatus(Integer status);
}
