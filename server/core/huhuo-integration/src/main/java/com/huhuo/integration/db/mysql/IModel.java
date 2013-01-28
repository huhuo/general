package com.huhuo.integration.db.mysql;

import java.io.Serializable;

public interface IModel<PK> extends Serializable {

	/**
	 * primary key attribute
	 */
	void setId(PK id);

	PK getId();

}
