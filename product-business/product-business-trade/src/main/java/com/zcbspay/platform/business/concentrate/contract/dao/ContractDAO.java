package com.zcbspay.platform.business.concentrate.contract.dao;

import com.zcbspay.platform.business.commons.dao.BaseDAO;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.contract.pojo.PojoContract;

public interface ContractDAO extends BaseDAO<PojoContract>{
	/**
	 * 根据合同编号查询合同信息
	 * @param debtorConsign
	 * @return
	 */
	public ContractBean queryContractByNum(String debtorConsign);
}
