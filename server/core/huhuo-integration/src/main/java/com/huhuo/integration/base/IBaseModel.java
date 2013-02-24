package com.huhuo.integration.base;

import java.io.Serializable;

public interface IBaseModel<PK> extends Serializable {

	/**
	 * primary key attribute
	 */
	void setId(PK id);

	PK getId();

}
