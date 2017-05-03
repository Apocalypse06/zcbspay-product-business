package com.zcbspay.platform.business.api.auth.dao;

import com.zcbspay.platform.business.api.auth.bean.MerchApiAuthBean;
import com.zcbspay.platform.business.api.auth.pojo.PojoMerchApiAuth;
import com.zcbspay.platform.business.commons.dao.BaseDAO;

public interface ApiAuthDAO extends BaseDAO<PojoMerchApiAuth>{
	/**
	 * 查询当前接口使用者是否开通的此接口业务
	 * @param merchApiAuthBean
	 * @return
	 */
	MerchApiAuthBean queryApiAuth(MerchApiAuthBean merchApiAuthBean);

}
