package com.eray.systemmanage.security.relation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface MapperAuthorityResource {
	Integer addCollection(@Param("authorityIds") List<Long> authorityIds,
			@Param("resourceIds") List<Long> resourceIds,
			@Param("type") Long type);
	Integer deleteByAuthorityId(@Param("authorityId")Long authorityId, @Param("type")Long type);
	Integer deleteByResourcedId(@Param("resourceId")Long resourceId, @Param("type")Long type);
	
	
	List<Map<String, Object>> relatedResources(@Param("resourceTbl")String resourceTbl, @Param("type")Long type, 
			@Param("authorityId")Long authorityId, @Param("start")Long start, @Param("limit")Long limit);
	List<Map<String, Object>> unrelatedResources(@Param("resourceTbl")String resourceTbl, @Param("type")Long type, 
			@Param("authorityId")Long authorityId, @Param("start")Long start, @Param("limit")Long limit);
	Long countRelatedResources(@Param("resourceTbl")String resourceTbl, @Param("type")Long type, 
			@Param("authorityId")Long authorityId);
	Long countUnrelatedResources(@Param("resourceTbl")String resourceTbl, @Param("type")Long type, 
			@Param("authorityId")Long authorityId);
	
}
