package com.zcbspay.platform.business.api.auth.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.api.auth.bean.MerchApiAuthBean;
import com.zcbspay.platform.business.api.auth.bean.ResultBean;
import com.zcbspay.platform.business.api.auth.dao.ApiAuthDAO;
import com.zcbspay.platform.business.api.auth.service.ApiAuthService;

@Service("apiAuthService")
@Transactional
public class ApiAuthServiceImpl implements ApiAuthService {
	private static final Logger logger = LoggerFactory.getLogger(ApiAuthServiceImpl.class);

	@Autowired
	private ApiAuthDAO apiAuthDAO;

	@Override
	public ResultBean queryApiAuth(MerchApiAuthBean merchApiAuthBean) {
		String merchID = merchApiAuthBean.getMemberid();
		String bizType = merchApiAuthBean.getBiztype();
		String txnType = merchApiAuthBean.getTxntype();
		String txnSubType = merchApiAuthBean.getTxnsubtype();
		if (StringUtils.isBlank(merchID) || StringUtils.isBlank(bizType) || StringUtils.isBlank(txnType)
				|| StringUtils.isBlank(txnSubType)) {
			logger.info("缺少参数，会员号或产品类型或交易类型或交易子类为空！");
			return new ResultBean("AA01", "缺少参数，会员号或产品类型或交易类型或交易子类为空！");
		}
		try {
			MerchApiAuthBean merchApiAuthBean2 = apiAuthDAO.queryApiAuth(merchApiAuthBean);
			if (merchApiAuthBean2 == null) {
				logger.info("没有该会员此业务的权限信息！");
				return new ResultBean("AA02", "没有该会员此业务的权限信息！");
			} else if (!merchApiAuthBean2.getStatus().equals("00")) {// 状态:00-有此交易权限，其他-没有此交易权限
				logger.info("没有此交易权限");
				return new ResultBean("AA03", "没有此交易权限！");
			} else {
				return new ResultBean(merchApiAuthBean2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询接口权限服务异常");
			return new ResultBean("AA04", "查询接口权限服务异常!");
		}
	}

}
