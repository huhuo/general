package com.eray.systemmanage.security.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.eray.systemmanage.common.IServBaseResource;
import com.eray.systemmanage.constant.EResourceType;
import com.eray.systemmanage.security.relation.IServUserRole;
import com.huhuo.integration.algorithm.MD5Utils;
import com.huhuo.integration.base.IBaseApplicationContextInit;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;
import com.huhuo.integration.exception.ServException;

@Service("smServUser")
public class ServUser implements IServUser, IBaseApplicationContextInit {
	@Resource(name="smMapperUser")
	private MapperUser mapperUser;
	@Resource(name="smServUserRole")
	private IServUserRole servUserRole;
	
	@SuppressWarnings("rawtypes")
	protected Map<EResourceType, IServBaseResource> mapResource = new HashMap<EResourceType, IServBaseResource>();
	
	@Override
	public Integer addBatch(List<ModelUser> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ModelUser> findByCondition(Condition<ModelUser> condition) {
		return findByCondition(condition, false);
	}

	@Override
	public List<ModelUser> findByCondition(
			Condition<ModelUser> condition, boolean injected) {
		if(condition==null){
			condition = new Condition<ModelUser>();
		}
		ModelUser t = condition.getT();
		if(t!=null && t.getPassword()!=null){
			t.setPassword(MD5Utils.encodeHex(t.getPassword()));
		}
		return mapperUser.findByCondition(condition);
	}

	@Override
	public Long countByCondition(Condition<ModelUser> condition) {
		if(condition==null){
			condition = new Condition<ModelUser>();
		}
		ModelUser t = condition.getT();
		if(t!=null && t.getPassword()!=null){
			t.setPassword(MD5Utils.encodeHex(t.getPassword()));
		}
		return mapperUser.countByCondition(condition);
	}

	@Override
	public Integer add(ModelUser t){
		if(t==null||t.getUsername()==null||t.getPassword()==null){
			throw new ServException("username 或 password 不能为空");
		}
		t.setPassword(MD5Utils.encodeHex(t.getPassword()));
		t.setCreateTime(new Date());
		return mapperUser.add(t);
	}

	@Override
	public <V> ModelUser find(V id) {
		return id == null ? null : mapperUser.find(id);
	}
	

	@Override
	public ModelUser findBy(String username, String password) {
		if (username == null || password == null)
			return null;
		return mapperUser.findBy(username, MD5Utils.encodeHex(password));
	}

	@Override
	public Integer delete(ModelUser t) {
		if(t==null || t.getId()==null)
			return null;
		Integer r = mapperUser.delete(t);
		if(r>0){
			servUserRole.deleteByUserId(t.getId());
		}
		return r;
	}

	@Override
	public Integer update(ModelUser t) {
		if(t==null||t.getUsername()==null||t.getPassword()==null){
			throw new ServException("username 或 password 不能为空");
		}
		t.setPassword(MD5Utils.encodeHex(t.getPassword()));
		return mapperUser.update(t);
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T>  getResource(ModelUser user, EResourceType type) {
		@SuppressWarnings("unchecked")
		IServBaseResource<T> servResource = mapResource.get(type);
		if(servResource==null){
			return new ArrayList<T>();
		}
		return servResource.getResource(user);
	}

	
	@Override
	public <T> boolean addServResource(IServBaseResource<T> servResource) {
		mapResource.put(servResource.getType(), servResource);
		return true;
	}

	/**
	 * 容器加载bean后，调用此函数用来注入资源ServBaseResource的实现类，令使用者可调用
	 * {@link #getResource(ModelUser, EResourceType)}来获取各种资源
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(ApplicationContext applicationContext) {
		@SuppressWarnings("rawtypes")
		Map<String, IServBaseResource> map =applicationContext.getBeansOfType(IServBaseResource.class);
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			addServResource(map.get(iter.next()));
		}
		return true;
	}

	@Override
	public ModelUser findBy(String username) {
		return mapperUser.findByName(username);
	}

	@Override
	public List<ModelUser> findModels(Page page) {
		// TODO Auto-generated method stub
		return null;
	}


}
