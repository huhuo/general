package com.eray.systemmanage.security.authority;

import java.io.Serializable;

import com.huhuo.integration.base.BaseModel;

public class ModelAuthority extends BaseModel implements Serializable{

	private static final long serialVersionUID = 6945099528295612516L;
	private String name;
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
