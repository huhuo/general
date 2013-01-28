package com.huhuo.integration.web;

import java.util.Map;

/**
 * message object for Ext.form.Basic in ExtJs
 * @author wuyuxuan
 * @param <T>
 */
public class FormMessage<T> extends Message<T> {

	/**
	 * the Criteria of Ext.form.Basic in ExtJs, used to justify the result of the submit action is success or failure,<br>
	 * and will invoke the relevant callback function
	 */
	protected boolean success;
	/**
	 * The errors property, which is optional, contains error messages for invalid fields.<br>
	 * cooperate with form validation in ExtJs
	 */
	protected Map<String, Object> errors;
	
	public FormMessage() {

	}

	public FormMessage(Status status, String msg) {
		super();
		setStatus(status);
		this.msg = msg;
	}

	public FormMessage(Status status, String msg, T data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
		switch (status) {
		case SUCCESS:
			this.success = true;
			break;
		default:
			this.success = false;
			break;
		}
	}

	public boolean getSuccess() {
		return this.success;
	}

}
