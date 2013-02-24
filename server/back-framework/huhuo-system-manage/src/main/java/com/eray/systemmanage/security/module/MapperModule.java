package com.eray.systemmanage.security.module;

import java.util.List;

import com.eray.systemmanage.common.MapperBaseResource;


public interface MapperModule extends MapperBaseResource<ModelModule>{

	/**
	 * 获取所有的菜单
	 * @return
	 */
	List<ModelModule> findAll();
	List<ModelModule> findByParentId(Long parentId);
	Integer updateLeafById(ModelModule t);
}
