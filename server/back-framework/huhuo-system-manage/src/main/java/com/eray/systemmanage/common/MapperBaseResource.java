package com.eray.systemmanage.common;

import java.util.List;

import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.base.IBaseExtenseMapper;

public interface MapperBaseResource<T> extends IBaseExtenseMapper<T>{
	List<T> getResource(ModelUser user);
}
