package com.huhuo.webbase.constant;

public class Constant {
	
	public enum GeneralPage {
		EXCEPTION_PAGE("msg", "/huhuo-web-base/exception"),
		MSG_PAGE("msg", "/huhuo-web-base/message"),
		;
		/** the key of data stored in request scope **/
		private String attrName;
		/** the location of rendering page the request will jump to **/
		private String location;
		GeneralPage(String attrName, String location) {
			this.attrName = attrName;
			this.location = location;
		}
		public String getAttrName() {
			return this.attrName;
		}
		public String getLocation() {
			return this.location;
		}
	}
	
}
