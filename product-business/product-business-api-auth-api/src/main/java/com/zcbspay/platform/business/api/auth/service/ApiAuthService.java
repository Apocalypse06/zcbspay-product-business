package com.zcbspay.platform.business.api.auth.service;

import com.zcbspay.platform.business.api.auth.bean.MerchApiAuthBean;
import com.zcbspay.platform.business.api.auth.bean.ResultBean;
/**
 * API接口权限管理
 * @author 	lianhai
 * @date	2017年4月14日
 */
public interface ApiAuthService {
	/**
	 * 查询当前接口使用者是否开通的此接口业务
	 * @param merchApiAuthBean
	 * @return
	 */
	ResultBean queryApiAuth(MerchApiAuthBean merchApiAuthBean);
}
