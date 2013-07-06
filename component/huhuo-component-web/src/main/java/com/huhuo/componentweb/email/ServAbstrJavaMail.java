package com.huhuo.componentweb.email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.huhuo.integration.base.BaseServ;
import com.huhuo.integration.exception.ServException;
/**
 * abstract class to implement primary method and function for interface IServJavaMail
 * @author kevin
 */
public abstract class ServAbstrJavaMail extends BaseServ implements IServJavaMail {

	public abstract JavaMailSender getJavaMailSender();
	
	public abstract String getSenderNickname();

	public abstract String getSenderUsername();
	
	@Override
	public void send(ModelJavaMail javamail) {
		try {
			MimeMessage msg = getJavaMailSender().createMimeMessage();
			MimeMessageHelper msgHelper = new MimeMessageHelper(msg, true, "UTF-8");
			msgHelper.setFrom(String.format("%s<%s>", getSenderNickname(), getSenderUsername()));
			msgHelper.setTo(javamail.getReceiver());
			msgHelper.setSubject(javamail.getTitle());
			msgHelper.setText(javamail.getContent());
			
			List<File> fileList =  javamail.getFileList();
			if (javamail.getFileList() != null) {
				for (int i = 0; i < fileList.size(); i++) {
					msgHelper.addAttachment(
							MimeUtility.encodeWord(fileList.get(i).getName()),
							fileList.get(i));
				}
			}
			getJavaMailSender().send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new ServException("message exception -->" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new ServException("unsupported encoding for email -->" + e.getMessage());
		}
	}
	
}
