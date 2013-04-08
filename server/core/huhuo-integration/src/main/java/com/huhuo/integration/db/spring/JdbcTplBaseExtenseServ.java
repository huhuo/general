package com.huhuo.integration.db.spring;

import java.util.List;

import com.huhuo.integration.base.BaseServ;
import com.huhuo.integration.base.IBaseExtenseDao;
import com.huhuo.integration.base.IBaseExtenseServ;
import com.huhuo.integration.db.mysql.Condition;
import com.huhuo.integration.db.mysql.Page;
import com.huhuo.integration.exception.DaoException;

/**
 * util service associated with DAO
 * @author wuyuxuan
 * @param <T>
 */
public abstract class JdbcTplBaseExtenseServ<T> extends BaseServ implements IBaseExtenseServ<T>{

	/**
	 * get DAO for this service
	 * @return
	 */
	public abstract IBaseExtenseDao<T> getDao();
	
	public abstract Class<T> getModelClazz();
	
	@Override
	public Integer add(T t) {
		return getDao().add(t);
	}

	@Override
	public <V> T find(V id) {
		return getDao().find(getModelClazz(), id);
	}

	@Override
	public Integer update(T t) {
		return getDao().update(t);
	}

	@Override
	public Long count() {
		return getDao().count();
	}

	@Override
	public Integer addBatch(List<T> list) {
		int[] addBatch = getDao().addBatch(list);
		return addBatch.length;
	}

	@Override
	public List<T> findByCondition(Condition<T> condition) {
		return getDao().findByCondition(condition);
	}

	@Override
	public List<T> findByCondition(Condition<T> condition, boolean injected) {
		return getDao().findByCondition(condition);
	}

	@Override
	public Long countByCondition(Condition<T> condition) {
		return getDao().countByCondition(condition);
	}

	@Override
	public List<T> findModels(Page<T> page) {
		if(page == null) {
			return null;
		}
		return  getDao().findModels(getModelClazz(), page.getStart(), page.getLimit());
	}
	@Override
	public Integer delete(T t) {
		return getDao().delete(t);
	}
	
	@Override
	public Integer deletePhysical(T t) throws DaoException {
		return getDao().deletePhysical(t);
	}
}
