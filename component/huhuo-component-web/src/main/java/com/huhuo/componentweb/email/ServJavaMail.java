package com.huhuo.componentweb.email;

import org.springframework.mail.javamail.JavaMailSender;

public class ServJavaMail extends ServAbstrJavaMail implements IServJavaMail {

	private JavaMailSender javaMailSender;
	
	private String senderNickname;
	
	private String senderUsername;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setSenderNickname(String senderNickname) {
		this.senderNickname = senderNickname;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	@Override
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	@Override
	public String getSenderNickname() {
		return senderNickname;
	}

	@Override
	public String getSenderUsername() {
		return senderUsername;
	}
	
}
