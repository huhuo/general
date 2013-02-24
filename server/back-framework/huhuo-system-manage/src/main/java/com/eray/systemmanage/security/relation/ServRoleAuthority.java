package com.eray.systemmanage.security.relation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.security.authority.ModelAuthority;

@Service("smServRoleAuthority")
public class ServRoleAuthority implements IServRoleAuthority {
	@Resource(name = "smMapperRoleAuthority")
	private MapperRoleAuthority mapperRoleAuthority;

	@Override
	public Integer addCollection(List<Long> roleIds, List<Long> authorityIds) {
		if(roleIds==null|| roleIds.size()==0 
				||authorityIds==null|| authorityIds.size()==0){
			return 0;
		}
		return mapperRoleAuthority.addCollection(roleIds, authorityIds);
	}

	@Override
	public Integer addCollection(Long roleId, List<Long> authorityIds) {
		if(roleId==null){
			return 0;
		}
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(roleId);
		
		return this.addCollection(roleIds, authorityIds);
	}

	@Override
	public Integer addCollection(List<Long> roleIds, Long authorityId) {
		if(authorityId==null){
			return 0;
		}
		List<Long> authorityIds = new ArrayList<Long>();
		roleIds.add(authorityId);
		
		return this.addCollection(roleIds, authorityIds);
	}

	@Override
	public Integer deleteByRoleId(Long roleId) {
		if(roleId==null){
			return 0;
		}
		return mapperRoleAuthority.deleteByRoleId(roleId);
	}

	@Override
	public Integer deleteByAuthorityId(Long authorityId) {
		if(authorityId==null){
			return 0;
		}
		return mapperRoleAuthority.deleteByAuthorityId(authorityId);
	}

	@Override
	public List<ModelAuthority> relatedAuthorities(Long roleId, Long start,
			Long limit) {
		if(roleId==null){
			return new ArrayList<ModelAuthority>(0);
		}
		return mapperRoleAuthority.relatedAuthorities(roleId, start, limit);
	}

	@Override
	public Long countRelatedAuthorities(Long roleId) {
		return roleId == null ? 0L : mapperRoleAuthority.countRelatedAuthorities(roleId);
	}

	@Override
	public List<ModelAuthority> unrelatedAuthorities(Long roleId, Long start,
			Long limit) {
		if (roleId == null) {
			return new ArrayList<ModelAuthority>(0);
		}
		return mapperRoleAuthority.unrelatedAuthorities(roleId, start, limit);

	}

	@Override
	public Long countUnrelatedAuthorities(Long roleId) {
		return roleId == null ? 0L : mapperRoleAuthority.countUnrelatedAuthorities(roleId);
	}
}
