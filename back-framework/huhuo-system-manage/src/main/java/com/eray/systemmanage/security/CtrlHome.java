package com.eray.systemmanage.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eray.systemmanage.constant.Constant;
import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.security.element.ModelElement;
import com.eray.systemmanage.security.module.ModelModule;
import com.eray.systemmanage.security.user.IServUser;
import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.base.BaseCtrl;

@Controller("smCtrlHome")
@RequestMapping("/sm")
public class CtrlHome extends BaseCtrl {
	
	protected String basePath = "system-manage/";
	@Resource(name = "smServUser")
	private IServUser servUser;
	
	
	@RequestMapping(value="/home.do")
	public String home(Model model, HttpSession session){
		logger.debug("access page-home");
		ModelUser user = (ModelUser)session.getAttribute(Constant.SESSION_USER);
		if(user!=null){
			//save resources
			List<ModelModule> modules = servUser.getResource(user, EResourceType.MODULE);
			saveModuleToSession(modules, session);
			List<ModelElement> elements = servUser.getResource(user, EResourceType.ELEMENT);
			saveElementToSession(elements, session);
		}
		
		return basePath+"home";
	}

	
	/**
	 * 按{@link ModelElement#getLocation()}将list分组
	 * 
	 * @param list
	 * @return Map, 其中 key为{@link ModelElement#getLocation()},value为ModelSysElement的列表
	 */
	protected void saveElementToSession(List<ModelElement> list, HttpSession session){
		Map<String, List<ModelElement>> r = new HashMap<String, List<ModelElement>>();
		if(list!=null){
			for(ModelElement ele: list){
				List<ModelElement> valueList = r.get(ele.getLocation());
				if(valueList==null){
					valueList = new ArrayList<ModelElement>();
					r.put(ele.getLocation(), valueList);
				}
				valueList.add(ele);
			}
		}
		session.setAttribute(Constant.SESSION_ELEMENT, r);
	}
	
	/**
	 * 1.将list中是三级菜单(一级菜单的parentId==0)的元素，组织到session中。session中key为
	 * {@link Constant#SESSION_MODULE}
	 * ，value为map，key为一级菜单；value为一个由key为二级菜单value为三级菜单列表的map<br>
	 * 2.将第一个三级菜单作为首页，将相关信息存到session中。key为
	 * {@link Constant#SESSION_MODULE_INDEX_PAGE}，value为第三级菜单；key为
	 * {@link Constant#SESSION_MODULE_INDEX_PANEL}，value为三级菜单对应的一级菜单
	 * 
	 * @param list
	 *            三级菜单列表
	 * @param session
	 */
	protected void saveModuleToSession(List<ModelModule> list, HttpSession session){
		Map<ModelModule, Map<ModelModule, List<ModelModule>>> r = new LinkedHashMap<ModelModule, Map<ModelModule,List<ModelModule>>>();
		session.setAttribute(Constant.SESSION_MODULE, r);
		boolean isSetIndex = false;
		for(ModelModule ele: list){
			ModelModule sec = ele.getParent();
			if(sec!=null){
				ModelModule fir = sec.getParent();
				if(fir!=null && fir.getParentId()!=null && fir.getParentId()==0L){
					Map<ModelModule, List<ModelModule>> firValue= r.get(fir);
					if(firValue==null){
						firValue = new LinkedHashMap<ModelModule, List<ModelModule>>();
						r.put(fir, firValue);
					}
					
					List<ModelModule> secValue = firValue.get(sec);
					if(secValue==null){
						secValue = new ArrayList<ModelModule>();
						firValue.put(sec, secValue);
					}
					secValue.add(ele);
					
					if(!isSetIndex){
						session.setAttribute(Constant.SESSION_MODULE_INDEX_PAGE, ele);
						session.setAttribute(Constant.SESSION_MODULE_INDEX_PANEL, fir);
						isSetIndex = true;
					}
				}
			}
		}
	}
}
