package com.eray.systemmanage.constant;

/**
 * 系统资源类
 * @author shifengxuan
 *
 */
public enum EResourceType {
	MODULE(1L),		//	sys_module中level为0的平台
	ELEMENT(2L),		//	页面元素
	PROVINCE(3L)		//	省份
	;
	
	public Long value = 0L;
	EResourceType(Long value){
		this.value = value;
	}
	
	public static EResourceType valueOf(Long value){
		if(value.equals(1L)){
			return MODULE;
		}else if(value.equals(2L)){
			return ELEMENT;
		}else if(value.equals(3L)){
			return PROVINCE;
		}
		return null;
	}
}
