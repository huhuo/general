package com.eray.systemmanage.security.authority;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.security.relation.IServAuthorityResource;
import com.eray.systemmanage.security.relation.IServRoleAuthority;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;

@Service("smServAuthority")
public class ServAuthority implements IServAuthority {
	@Resource(name="smMapperAuthority")
	private MapperAuthority mapperAuthority;
	@Resource(name="smServRoleAuthority")
	private IServRoleAuthority servRoleAuthority;
	@Resource(name="smServAuthorityResource")
	private IServAuthorityResource servAuthorityResource;
	
	@Override
	public Integer addBatch(List<ModelAuthority> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ModelAuthority> findByCondition(
			Condition<ModelAuthority> condition) {
		return findByCondition(condition, false);
	}

	@Override
	public List<ModelAuthority> findByCondition(
			Condition<ModelAuthority> condition, boolean injected) {
		if (condition == null) {
			condition = new Condition<ModelAuthority>();
		}
		return mapperAuthority.findByCondition(condition);
	}

	@Override
	public Long countByCondition(Condition<ModelAuthority> condition) {
		if(condition==null){
			condition = new Condition<ModelAuthority>();
		}
		return mapperAuthority.countByCondition(condition);
	}

	@Override
	public Integer add(ModelAuthority t) {
		return t == null ? 0 : mapperAuthority.add(t);
	}

	@Override
	public <V> ModelAuthority find(V id) {
		return id == null? null : mapperAuthority.find(id);
	}

	@Override
	public Integer delete(ModelAuthority t) {
		if(t==null||t.getId()==null){
			return 0;
		}
		int r = mapperAuthority.delete(t);
		if(r>0){
			servRoleAuthority.deleteByAuthorityId(t.getId());
			servAuthorityResource.deleteByAuthorityId(t.getId());
		}
		return r;
	}

	@Override
	public Integer update(ModelAuthority t) {
		return (t == null || t.getId() == null) ? 0 : mapperAuthority.update(t);
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelAuthority> findModels(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
