package com.eray.systemmanage.security.relation;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.systemmanage.security.role.ModelRole;


public interface MapperUserRole{
	Integer addCollection(@Param("userIds")List<Long> userIds, @Param("roleIds")List<Long> roleIds);
	Integer deleteByUserId(Long userId);
	Integer deleteByRoleId(Long roleId);
	List<ModelRole> unrelatedRoles(@Param("userId")Long userId, @Param("start")Long start, @Param("limit")Long limit);
	Long countUnrelatedRoles(Long userId);
	List<ModelRole> relatedRoles(@Param("userId")Long userId, @Param("start")Long start, @Param("limit")Long limit);
	Long countRelatedRoles(Long userId);
}
