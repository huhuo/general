package com.eray.systemmanage.province;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.constant.MemcachedRegion;
import com.eray.systemmanage.security.relation.IServAuthorityResource;
import com.eray.systemmanage.security.user.ModelUser;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;
import com.huhuo.integration.service.MemcachedServ;
import com.huhuo.integration.util.StringUtils;

@Service("smServProvince")
public class ServProvince implements IServProvince {
	@Resource(name = "smMapperProvince")
	private MapperProvince mapperProvince;
	@Resource(name = "memcachedService")
	private MemcachedServ servMemcached;
	@Resource(name="smServAuthorityResource")
	private IServAuthorityResource servAuthorityResource;
	
	private int version=0;
	protected String region = MemcachedRegion.ProvinceRegion.region();
	protected String separator = "_";
	
	@Override
	public Integer addBatch(List<ModelProvince> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ModelProvince> findByCondition(
			Condition<ModelProvince> condition) {
		return findByCondition(condition, false);
	}

	@Override
	public List<ModelProvince> findByCondition(
			Condition<ModelProvince> condition, boolean injected) {
		if(condition==null){
			condition = new Condition<ModelProvince>();
		}
		List<ModelProvince> list = mapperProvince.findByCondition(condition);
		return list;
	}

	@Override
	public Long countByCondition(Condition<ModelProvince> condition) {
		if(condition==null){
			condition = new Condition<ModelProvince>();
		}
		return mapperProvince.countByCondition(condition);
	}

	@Override
	public Integer add(ModelProvince t) {
		return (t != null && t.getId() != null) ? mapperProvince.add(t) : 0;
	}

	@Override
	public <V> ModelProvince find(V id) {
		ModelProvince r = null;
		if(id!=null){
			String key = getKey(id);
			r= servMemcached.get(region, key);
			if(r==null){
				r = mapperProvince.find(id);
				if(r!=null){
					servMemcached.set(region, key, r);
				}
			}
		}
		return r;
	}

	@Override
	public Integer delete(ModelProvince t) {
		if (t != null && t.getId() != null) {
			version ++;
			Integer row = mapperProvince.delete(t);
			if(row>0){
				servAuthorityResource.deleteByResourcedId(t.getId(), EResourceType.PROVINCE);
			}
			return row;
		}
		return 0;
	}

	@Override
	public Integer update(ModelProvince t) {
		if (t != null && t.getId() != null) {
			version ++;
			return mapperProvince.update(t);
		}
		return 0;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}
 
	public <V> String getKey(V id){
		return StringUtils.join(separator, id, version);
	}

	@Override
	public List<ModelProvince> getResource(ModelUser user) {
		List<ModelProvince> r = null;
		if(ModelUser.ADMIN.equals(user.getUsername())){
			r = this.findByCondition(null);
		}else{
		
			if(user.getId()==null){
				r = new ArrayList<ModelProvince>(0);
			}else{
				r = mapperProvince.getResource(user);
			}
		}
		return r;
	}

	@Override
	public EResourceType getType() {
		return EResourceType.PROVINCE;
	}

	@Override
	public List<ModelProvince> findModels(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
