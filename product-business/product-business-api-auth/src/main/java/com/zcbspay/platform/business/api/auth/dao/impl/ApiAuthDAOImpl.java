package com.zcbspay.platform.business.api.auth.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zcbspay.platform.business.api.auth.bean.MerchApiAuthBean;
import com.zcbspay.platform.business.api.auth.dao.ApiAuthDAO;
import com.zcbspay.platform.business.api.auth.pojo.PojoMerchApiAuth;
import com.zcbspay.platform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;

@Repository
public class ApiAuthDAOImpl extends HibernateBaseDAOImpl<PojoMerchApiAuth> implements ApiAuthDAO {
	@Override
	public MerchApiAuthBean queryApiAuth(MerchApiAuthBean merchApiAuthBean) {
		Criteria criteria = this.getSession().createCriteria(PojoMerchApiAuth.class);
		criteria.add(Restrictions.eq("memberid", merchApiAuthBean.getMemberid()));
		criteria.add(Restrictions.eq("biztype", merchApiAuthBean.getBiztype()));
		criteria.add(Restrictions.eq("txntype", merchApiAuthBean.getTxntype()));
		criteria.add(Restrictions.eq("txnsubtype", merchApiAuthBean.getTxnsubtype()));
		Object uniqueResult = criteria.uniqueResult(); 
		return uniqueResult == null ? null : BeanCopyUtil.copyBean(MerchApiAuthBean.class, uniqueResult);
	}

}
