package com.eray.systemmanage.security.user;

import java.util.List;

import com.eray.systemmanage.common.IServBaseResource;
import com.eray.systemmanage.constant.EResourceType;
import com.huhuo.integration.base.IBaseExtenseServ;

public interface IServUser extends IBaseExtenseServ<ModelUser> {
	/**
	 * Get resource, for example, module,element and province and so on. The
	 * resource is belong to user.
	 * 
	 * @param user
	 * @param type
	 * @return ModelSysModule list when type={@link EResourceType#MODULE}
	 *         .value<br>
	 *         ModelSysElement list when type={@link EResourceType#ELEMENT}
	 *         .value<br>
	 *         ModelSysProvince list when type={@link EResourceType#PROVINCE}
	 *         .value<br>
	 */
	<T> List<T> getResource(ModelUser user, EResourceType type);
	<T> boolean addServResource(IServBaseResource<T> servSysResource);
	ModelUser findBy(String username, String password);
	ModelUser findBy(String username);
}
