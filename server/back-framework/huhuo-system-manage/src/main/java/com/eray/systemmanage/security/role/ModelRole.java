package com.eray.systemmanage.security.role;

import java.io.Serializable;

import com.eray.systemmanage.security.module.ModelModule;
import com.huhuo.integration.base.BaseModel;

public class ModelRole extends BaseModel implements Serializable {

	private static final long serialVersionUID = 8545138048165692898L;
	private String name;
	private String comment;

	private ModelModule module;

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

	public ModelModule getModule() {
		return module;
	}

	public void setModule(ModelModule module) {
		this.module = module;
	}

}
