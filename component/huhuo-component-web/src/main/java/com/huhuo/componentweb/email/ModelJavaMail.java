package com.huhuo.componentweb.email;

import java.io.File;
import java.util.List;

import com.huhuo.integration.base.BaseModel;
/**
 * an java mail entity for an complete e-mail
 * @author kevin
 */
public class ModelJavaMail extends BaseModel {

	private static final long serialVersionUID = -2976640727785816493L;
	
	/** 邮件主题 */
	private String title;
	/** 邮件内容 */
	private String content;
	/** 收件人 */
	private String receiver;
	
	/** 附件列表 */
	private List<File> fileList;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public List<File> getFileList() {
		return fileList;
	}
	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}
	
}
