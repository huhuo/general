package com.eray.systemmanage.security;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.constant.Constant;
import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.province.ModelProvince;
import com.eray.systemmanage.security.user.IServUser;
import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.integration.util.ExtUtils;
import com.huhuo.integration.web.Message;
import com.huhuo.integration.web.Message.Status;


/**
 * Provide an interface for accessing common resources like province, city,
 * dictionary and so on.
 * 
 * @author shifengxuan
 * 
 */
@Controller("smCtrlRes")
@RequestMapping(value="/sm/security/res")
public class CtrlRes extends BaseCtrl{
	
	@Resource(name = "smServUser")
	private IServUser servUser;
	/*@Resource(name = "smServSysModule")
	private IServSysModule servModule;*/
	
	@RequestMapping(value="/province.do")
	public void getProvince(boolean includeAll, OutputStream out, HttpSession session){
		try{
			logger.debug("server receive: includeAll={}", includeAll);
			
			List<ModelProvince> list = servUser.getResource(
					(ModelUser)session.getAttribute(Constant.SESSION_USER), EResourceType.PROVINCE);
			if(includeAll){
				ModelProvince p = new ModelProvince();
				p.setId(ModelProvince.ALL_PROVINCE_ID);
				p.setName(ModelProvince.ALL_PROVINCE_NAME);
				list.add(0, p);
			}
			write(ExtUtils.getJsonStore(list, (long)list.size()), out);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			write(new Message<String>(Status.ERROR, e.getMessage()), out);
		}
	}

}
