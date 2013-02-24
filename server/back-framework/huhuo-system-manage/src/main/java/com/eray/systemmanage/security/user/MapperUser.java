package com.eray.systemmanage.security.user;

import org.apache.ibatis.annotations.Param;

import com.huhuo.integration.base.IBaseExtenseMapper;


public interface MapperUser extends IBaseExtenseMapper<ModelUser> {
	ModelUser findBy(@Param("username")String username, @Param("password")String password);
	ModelUser findByName(String username);
}
