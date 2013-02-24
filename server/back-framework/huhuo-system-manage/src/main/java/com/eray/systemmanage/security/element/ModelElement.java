package com.eray.systemmanage.security.element;

import java.io.Serializable;

import com.huhuo.integration.base.BaseModel;

public class ModelElement extends BaseModel implements Serializable {

	private static final long serialVersionUID = 8232865850043962446L;
	private String name;
	private String location;
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
