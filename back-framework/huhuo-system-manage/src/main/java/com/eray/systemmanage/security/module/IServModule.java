package com.eray.systemmanage.security.module;

import java.util.List;
import java.util.Map;

import com.eray.systemmanage.common.IServBaseResource;

public interface IServModule extends IServBaseResource<ModelModule>{
	/**
	 * 获取所有的菜单
	 * @return
	 */
	List<ModelModule> findAll();
	
	public Map<ModelModule, List<ModelModule>> getModuleGroupedByParentId();
	public List<ModelModule> findByParentId(Long parentId);
	Integer updateLeafById(Long id, boolean leaf);
}
