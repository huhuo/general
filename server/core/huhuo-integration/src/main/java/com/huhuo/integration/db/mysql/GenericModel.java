package com.huhuo.integration.db.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huhuo.integration.base.BaseModel;

public abstract class GenericModel extends BaseModel implements IModel<Long> {

	private static final long serialVersionUID = 8669799836108015546L;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * primary key
	 */
	protected Long id;
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	
}
