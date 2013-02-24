package com.eray.systemmanage.security;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.constant.Constant;
import com.eray.systemmanage.security.user.IServUser;
import com.eray.systemmanage.security.user.ModelUser;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.util.StringUtils;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

@Controller("smLogin")
//@RequestMapping(value="/sm/enter")
public class CtrlLogin extends BaseCtrl {
	protected String basePath = "system-manage/";
	@Resource(name = "smCaptchaProducer")
	private Producer captchaProducer;
	@Resource(name = "smServUser")
	private IServUser servUser;
	
	@RequestMapping(value="/")
	public String index(){
		return basePath + "login";
	}
	
	@RequestMapping(value="/sm/enter/login.do")
	public void login(HttpServletRequest req, HttpServletResponse resp,HttpSession session, ModelUser user, OutputStream out){
		try{
			user = servUser.findBy(user.getUsername(), user.getPassword());
			Object kaptchaInSession = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			Object kaptchaInReq = req.getParameter("captcha");
			
			if(user != null) {
				if(kaptchaInSession==null){
					session.setAttribute(Constant.SESSION_LOGIN_ERR_MSG, "验证码过期");
				}else if(!kaptchaInSession.equals(kaptchaInReq)){
					session.setAttribute(Constant.SESSION_LOGIN_ERR_MSG,"验证码错误");
				}else{
					//save user
					session.setAttribute(Constant.SESSION_USER, user);
					session.setAttribute(Constant.SESSION_LOGIN_ERR_MSG, StringUtils.EMPTY);
				}
			}else{
				session.setAttribute(Constant.SESSION_LOGIN_ERR_MSG, "用户名或密码错误");
			}
			resp.sendRedirect(req.getContextPath()+"/sm/home.do");
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/sm/enter/logout.do")
	public String logout(HttpSession session){
		session.removeAttribute(Constant.SESSION_USER);
		session.removeAttribute(Constant.SESSION_MODULE);
		return index();
	}
	
	@RequestMapping(value="/sm/enter/captcha.do")
	public void get(HttpServletRequest request, HttpServletResponse response, OutputStream out) {
		try{
			response.setDateHeader("Expires", 0);
			// Set standard HTTP/1.1 no-cache headers.
			response.setHeader("Cache-Control",
					"no-store, no-cache, must-revalidate");
			// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			// Set standard HTTP/1.0 no-cache header.
			response.setHeader("Pragma", "no-cache");
			// return a jpeg
			response.setContentType("image/jpeg");
			// create the text for the image
			String capText = captchaProducer.createText();
			// store the text in the session
			request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,
					capText);
			// create the image with the text
			BufferedImage bi = captchaProducer.createImage(capText);
			//ServletOutputStream out = response.getOutputStream();
			// write the data out
			ImageIO.write(bi, "jpg", out);
			try {
				out.flush();
			} finally {
				out.close();
			}
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}

}
