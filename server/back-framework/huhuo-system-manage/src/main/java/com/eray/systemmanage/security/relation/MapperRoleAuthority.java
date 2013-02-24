package com.eray.systemmanage.security.relation;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.systemmanage.security.authority.ModelAuthority;

public interface MapperRoleAuthority {
	Integer addCollection(@Param("roleIds") List<Long> roleIds,
			@Param("authorityIds") List<Long> authorityIds);
	Integer deleteByRoleId(Long roleId);
	Integer deleteByAuthorityId(Long authorityId);
	
	List<ModelAuthority> relatedAuthorities(@Param("roleId")Long roleId, @Param("start")Long start, @Param("limit")Long limit);
	Long countRelatedAuthorities(Long roleId);
	
	List<ModelAuthority> unrelatedAuthorities(@Param("roleId")Long roleId, @Param("start")Long start, @Param("limit")Long limit);
	Long countUnrelatedAuthorities(Long roleId);
	
	
}
