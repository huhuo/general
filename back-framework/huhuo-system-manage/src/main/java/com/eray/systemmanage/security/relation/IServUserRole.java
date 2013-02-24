package com.eray.systemmanage.security.relation;

import java.util.List;

import com.eray.systemmanage.security.role.ModelRole;


public interface IServUserRole{
	Integer addCollection(List<Long> userIds, List<Long> roleIds);
	Integer addCollection(Long userId, List<Long> roleIds);
	Integer addCollection(List<Long> userIds, Long roleId);
	
	Integer deleteByUserId(Long userId);
	Integer deleteByRoleId(Long roleId);
	
	List<ModelRole> unrelatedRoles(Long userId, Long start, Long limit);
	Long countUnrelatedRoles(Long userId);
	List<ModelRole> relatedRoles(Long userId, Long start, Long limit);
	Long countRelatedRoles(Long userId);
}
