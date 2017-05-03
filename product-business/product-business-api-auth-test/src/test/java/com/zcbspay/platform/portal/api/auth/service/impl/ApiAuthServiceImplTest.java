package com.zcbspay.platform.portal.api.auth.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zcbspay.platform.business.api.auth.bean.MerchApiAuthBean;
import com.zcbspay.platform.business.api.auth.bean.ResultBean;
import com.zcbspay.platform.business.api.auth.service.ApiAuthService;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("/spring-context.xml")
public class ApiAuthServiceImplTest {

	@Autowired
	private ApiAuthService apiAuthService;

	@Test
	public void testQueryApiAuth() {
		MerchApiAuthBean merchApiAuthBean = new MerchApiAuthBean();
		merchApiAuthBean.setMemberid("1");
		merchApiAuthBean.setBiztype("1");
		merchApiAuthBean.setTxntype("1");
		merchApiAuthBean.setTxnsubtype("1");

		ResultBean resultBean = apiAuthService.queryApiAuth(merchApiAuthBean);
		if (!resultBean.isResultBool()) {
			System.out.println(resultBean.getErrMsg());
		} else {
			merchApiAuthBean = (MerchApiAuthBean) resultBean.getResultObj();
			System.out.println(merchApiAuthBean.toString());
		}
	}

}
