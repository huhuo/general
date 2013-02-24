package com.eray.systemmanage.security.user;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.constant.Constant;
import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.FormMessage;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;

@Controller("smCtrlUser")
@RequestMapping(value="/sm/security/user")
public class CtrlUser extends BaseCtrl {
	private String basePath = "system-manage/security/user/";
	@Resource(name = "smServUser")
	private IServUser servUser;
	
	@RequestMapping(value="/index.do")
	public String index(){
		return basePath + "index";
	}
	
	@RequestMapping(value="/get.do")
	public void get(Condition<ModelUser> condition, ModelUser user, OutputStream out){
		logger.debug("server receive: condiction={}, user={}", 
				new Object[] {condition, user});
		try{
			condition.setT(user);
			List<ModelUser> list =  servUser.findByCondition(condition);
			Long count = servUser.countByCondition(condition);
			write(ExtUtils.getJsonStore(list, count), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(ModelUser user, OutputStream out, HttpSession session){
		logger.debug("server receive: user={}", user);
		
		try{
			Message<String> msg = new Message<String>(Status.FAILURE, "删除用户失败");
			if(!validAuthority(session, user)){
				msg.setMsg("无权操作");
			}else{
				int r = servUser.delete(user);
				if(r>0){
					msg.setStatus(Status.SUCCESS);
					msg.setMsg("删除用户成功");
				}
			}
			write(msg, out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}
	
	@RequestMapping(value="/save.do")
	public void save(ModelUser user, OutputStream out, HttpSession session){
		logger.debug("server receive: user={}", user);
		FormMessage<String> r = new FormMessage<String>();
		r.setStatus(Status.SUCCESS);
		try{
			if(user!=null){
				int row;
				ModelUser userExist = servUser.findBy(user.getUsername());
				
				if(user.getId()!=null){
					if(userExist!=null && !userExist.getId().equals(user.getId())){
						r.setStatus(Status.FAILURE);
						r.setMsg("用户名已存在");
					}else{
						if(!validAuthority(session, user)){
							r.setStatus(Status.FAILURE);
							r.setMsg("无权更新");
						}else{
							row = servUser.update(user);
							if(row<=0){
								r.setStatus(Status.FAILURE);
								r.setMsg("更新失败");
							}else{
								r.setMsg("更新成功");
							}
						}
					}
				}else{
					if(userExist!=null){
						r.setStatus(Status.FAILURE);
						r.setMsg("用户名已存在");
					}else{
						row = servUser.add(user);
						if(row<=0){
							r.setStatus(Status.FAILURE);
							r.setMsg("添加失败");
						}else{
							r.setMsg("添加成功");
						}
					}
				}
				
				
			}else{
				r.setStatus(Status.FAILURE);
				r.setMsg("操作失败，用户为空");
			}
		}catch (Exception e) {
			r.setStatus(Status.ERROR);
			r.setMsg("操作失败");
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		write(r, out);
	}
	
	@RequestMapping(value="/get/{id}.do")
	public void getById(@PathVariable("id") String id, OutputStream out){
		logger.debug("server receive: userid={}", id);
		FormMessage<ModelUser> m = new FormMessage<ModelUser>();
		try{
			ModelUser r = servUser.find(id);
			
			m.setData(r);
			m.setStatus(Status.SUCCESS);
			if(r==null){
				m.setStatus(Status.FAILURE);
			}else{
				r.setPassword(null);
			}
			
		}catch (Exception e) {
			m.setStatus(Status.ERROR);
			m.setMsg("操作失败");
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		write(m, out);
	}
	
	/**
	 * 验证存于session中的登录用户对user是否有修改权限
	 * 
	 * @param session
	 * @param user
	 * @return true: 有权限; false: 无权限
	 */
	protected boolean validAuthority(HttpSession session, ModelUser user){
		ModelUser loginUser = (ModelUser)session.getAttribute(Constant.SESSION_USER);
		if(user.getUsername()==ModelUser.ADMIN && loginUser.getUsername()!=ModelUser.ADMIN){
			return false;
		}
		return true;
	}
}
