package com.eray.systemmanage.security.module;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.httpclient.util.LangUtils;

import com.huhuo.integration.base.BaseModel;

public class ModelModule extends BaseModel implements Serializable,
		Comparable<ModelModule> {

	private static final long serialVersionUID = -1829630891254266752L;
	
	private String name;
	private String url;
	private Long parentId;
	private ModelModule parent;
	private Integer orderNo;
	private Boolean visible;
	private String icon;
	private Integer level;
	private Boolean leaf;
	private Date createTime;
	private Date updateTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public ModelModule getParent() {
		return parent;
	}

	public void setParent(ModelModule parent) {
		this.parent = parent;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int compareTo(ModelModule o) {
		int thisId = Integer.parseInt(this.id.toString());
		int cmpId = Integer.parseInt(o.id.toString());
		return thisId - cmpId;
	}

	@Override
	public boolean equals(Object obj) {
		boolean ret =false;
		if (obj instanceof ModelModule) {
			ModelModule module = (ModelModule) obj;
			if(this.id!=null){
				ret = this.id.equals(module.getId());
			}else if(module.getId()==null){ // both of id are null ==> equal 
				ret = true;
			}
			
		} else {
			ret = super.equals(obj);
		}
		return ret;
	}
	
	@Override
	public int hashCode() {
		int r = LangUtils.HASH_SEED;
		r = LangUtils.hashCode(r, this.id);
		return r;
	}

}
