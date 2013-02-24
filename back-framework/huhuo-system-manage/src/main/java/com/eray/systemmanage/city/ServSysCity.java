package com.eray.systemmanage.city;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.systemmanage.constant.MemcachedRegion;
import com.eray.systemmanage.province.IServProvince;
import com.eray.systemmanage.province.ModelProvince;
import com.huhuo.integration.base.BaseServ;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;
import com.huhuo.integration.service.MemcachedServ;
import com.huhuo.integration.util.StringUtils;

@Service("smServSysCity")
public class ServSysCity extends BaseServ implements IServSysCity {
	@Resource(name = "smMapperSysCity")
	private MapperSysCity mapperSysCity;
	@Resource(name = "smServProvince")
	private IServProvince servSysProvince;
	@Resource(name = "memcachedService")
	private MemcachedServ servMemcached;
	
	protected String region = MemcachedRegion.CityRegion.region();
	protected int version = 0;
	protected String separator = "_";
	
	@Override
	public Integer addBatch(List<ModelSysCity> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ModelSysCity> findByCondition(Condition<ModelSysCity> condition) {
		return findByCondition(condition, false);
	}

	@Override
	public List<ModelSysCity> findByCondition(
			Condition<ModelSysCity> condition, boolean injected) {
		if (condition == null) {
			condition = new Condition<ModelSysCity>();
		}
		List<ModelSysCity> list = mapperSysCity.findByCondition(condition);
		if (injected) {
			for (ModelSysCity city : list) {
				ModelProvince province = servSysProvince.find((long) city
						.getProvinceId());
				if (province == null) {
					province = new ModelProvince();
					province.setId(city.getProvinceId());
				}
				city.setProvince(province);
			}
		}

		return list;
	}

	@Override
	public Long countByCondition(Condition<ModelSysCity> condition) {
		if (condition == null) {
			condition = new Condition<ModelSysCity>();
		}
		return mapperSysCity.countByCondition(condition);
	}

	@Override
	public Integer add(ModelSysCity t) {
		return (t != null && t.getId() != null) ? mapperSysCity.add(t) : 0;

	}

	@Override
	public <V> ModelSysCity find(V id) {
		ModelSysCity r = null;
		if(id!=null){
			String key = getKey(id);
			r = servMemcached.get(region, key);
			if(r==null){
				r = mapperSysCity.find(id);
				if(r!=null)
					servMemcached.set(region, key, r);
			}
		}
		return r;
	}

	@Override
	public Integer delete(ModelSysCity t) {
		if(t != null && t.getId() != null) {
			version ++;
			return mapperSysCity.delete(t);
		}
		return 0;
	}

	@Override
	public Integer update(ModelSysCity t) {
		if(t != null && t.getId() != null) {
			version ++;
			return mapperSysCity.update(t);
		}
		return 0;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected <V> String getKey(V id){
		return StringUtils.join(separator, id, version);
	}

	@Override
	public List<ModelSysCity> findModels(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
