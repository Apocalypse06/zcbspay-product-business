package com.zcbspay.platform.business.concentrate.contract.dao.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zcbspay.platform.business.commons.dao.base.HibernateBaseDAOImpl;
import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.contract.pojo.PojoContract;

@Repository
public class ContractDAOImpl extends HibernateBaseDAOImpl<PojoContract> implements ContractDAO {

	@Override
	public ContractBean queryContractByNum(String debtorConsign) {
		Criteria criteria = this.getSession().createCriteria(PojoContract.class);
		criteria.add(Restrictions.eq("contractNum", debtorConsign));
		Object uniqueResult = criteria.uniqueResult();
		return BeanCopyUtil.copyBean(ContractBean.class, uniqueResult);
	}

	public String checkContract(String contractnum, String merchno, String debtorname, String debtoraccountno,
			String creditorname, String creditoraccountno, String contracttype, String amount, String debtorbranchcode,
			String creditorbranchcode) {
		String sql = "SELECT fnc_getcontract(?,?,?,?,?,?,?,?,?,?) AS RSP FROM dual";
		Object paramaters[] = { contractnum, merchno, debtorname, debtoraccountno, creditorname, creditoraccountno,
				contracttype, amount, debtorbranchcode, creditorbranchcode};
		@SuppressWarnings("unchecked")
		Map<String, Object> rsp = (Map<String, Object>) this.queryBySQL(sql, paramaters).get(0);
		return rsp.get("RSP").toString();
	}
}
