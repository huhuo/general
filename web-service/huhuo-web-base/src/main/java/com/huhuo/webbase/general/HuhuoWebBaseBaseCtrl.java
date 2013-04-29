package com.huhuo.webbase.general;

import org.springframework.ui.Model;

import com.huhuo.integration.base.BaseCtrl;
import com.huhuo.webbase.constant.Constant.GeneralPage;

public class HuhuoWebBaseBaseCtrl extends BaseCtrl {

	/**
	 * return render page with obj in JSON format
	 * @param model request scope to which the obj will be store
	 * @param obj content return to client
	 * @return the location of render page
	 */
	protected String render(Model model, Object obj) {
		model.addAttribute(GeneralPage.MSG_PAGE.getAttrName(), obj);
		logger.info("==> return message --> {}", obj);
		return GeneralPage.MSG_PAGE.getLocation();
	}
	
}
