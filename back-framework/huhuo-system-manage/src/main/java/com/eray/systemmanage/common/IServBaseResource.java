package com.eray.systemmanage.common;

import java.util.List;

import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.base.IBaseExtenseServ;

public interface IServBaseResource<T> extends IBaseExtenseServ<T>{
	List<T> getResource(ModelUser user);
	EResourceType getType();
}
