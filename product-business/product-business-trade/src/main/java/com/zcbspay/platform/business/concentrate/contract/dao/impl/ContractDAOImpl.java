package com.zcbspay.platform.business.concentrate.contract.dao.impl;

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
			String creditorname, String creditoraccountno, String contracttype, String amount) {
		String sql = "SELECT fnc_getcontract(?,?,?,?,?,?,?,?) FROM	dual;";
		Object paramaters[] = { contractnum, merchno, debtorname, debtoraccountno, creditorname, creditoraccountno,
				contracttype, amount };
		return (String) this.queryBySQL(sql, paramaters).get(0);
	}
}
