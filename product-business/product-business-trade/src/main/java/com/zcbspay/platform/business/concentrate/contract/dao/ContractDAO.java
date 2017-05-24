package com.zcbspay.platform.business.concentrate.contract.dao;

import com.zcbspay.platform.business.commons.dao.BaseDAO;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.contract.pojo.PojoContract;

public interface ContractDAO extends BaseDAO<PojoContract> {
	/**
	 * 根据合同编号查询合同信息
	 * 
	 * @param debtorConsign
	 * @return
	 */
	public ContractBean queryContractByNum(String debtorConsign);

	/**
	 * 校验合同信息
	 * 
	 * @param contractnum
	 *            合同编号
	 * @param merchno
	 *            商户号
	 * @param debtorname
	 *            付款人名称
	 * @param debtoraccountno
	 *            付款人账号
	 * @param creditorname
	 *            收款人名称
	 * @param creditoraccountno
	 *            收款人账号
	 * @param contracttype
	 *            合同收付类型 CT00-批量代收协议;CT01-批量代付代付协议;CT02-实时代收协议；CT03-实时代付协议 CT99：代收付协议
	 * @param amount
	 *            付款金额
	 * @param debtorbranchcode
	 *            付款人银行号
	 * @param amount
	 *            收款人银行号
	 * @return 校验结果（格式为：“响应码,响应信息”）
	 */
	public String checkContract(String contractnum, String merchno, String debtorname, String debtoraccountno,
			String creditorname, String creditoraccountno, String contracttype, String amount, String debtorbranchcode,
			String creditorbranchcode);
}
