package com.eray.systemmanage.security.relation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.security.role.ModelRole;

@Service("smServUserRole")
public class ServUserRole implements IServUserRole {
	@Resource(name="smMapperUserRole")
	private MapperUserRole mapperUserRole;
	
	@Override
	public Integer addCollection(List<Long> userIds, List<Long> roleIds) {
		if(userIds==null || roleIds==null ||userIds.size()==0 || roleIds.size()==0){
			return 0;
		}
			
		return mapperUserRole.addCollection(userIds, roleIds);
	}

	@Override
	public Integer addCollection(Long userId, List<Long> roleIds) {
		if(userId==null){
			return 0;
		}
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(userId);
		return addCollection(userIds, roleIds);
	}

	@Override
	public Integer addCollection(List<Long> userIds, Long roleId) {
		if(roleId==null){
			return 0;
		}
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(roleId);
		
		return addCollection(userIds, roleIds);
	}

	@Override
	public Integer deleteByUserId(Long userId) {
		if(userId==null){
			return 0;
		}
		return mapperUserRole.deleteByUserId(userId);
	}

	@Override
	public Integer deleteByRoleId(Long roleId) {
		if(roleId==null){
			return 0;
		}
		return mapperUserRole.deleteByRoleId(roleId);
	}

	@Override
	public List<ModelRole> unrelatedRoles(Long userId, Long start, Long limit) {
		if(userId==null){
			return new ArrayList<ModelRole>(0);
		}
		return mapperUserRole.unrelatedRoles(userId, start, limit);
	}

	@Override
	public Long countUnrelatedRoles(Long userId) {
		if(userId==null){
			return 0L;
		}
		return mapperUserRole.countUnrelatedRoles(userId);
	}

	@Override
	public List<ModelRole> relatedRoles(Long userId, Long start, Long limit) {
		if(userId==null){
			return new ArrayList<ModelRole>(0);
		}
		return mapperUserRole.relatedRoles(userId, start, limit);
	}

	@Override
	public Long countRelatedRoles(Long userId) {
		if(userId==null){
			return 0L;
		}
		return mapperUserRole.countRelatedRoles(userId);
	}

}
