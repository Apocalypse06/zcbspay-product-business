package com.zcbspay.platform.business.concentrate.realtime.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.EncryptDataBean;
import com.zcbspay.platform.business.concentrate.bean.RealtimePaymentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.realtime.service.RealtimePayment;

import net.sf.json.JSONObject;

@Service
public class RealtimePaymentImpl implements RealtimePayment {
	@Autowired
	private ContractDAO contractDAO;

	@Override
	public ResultBean pay(RealtimePaymentBean realtimePaymentBean) {
		if (realtimePaymentBean == null) {
			return new ResultBean("BP0000", "参数不能为空！");
		}

		// 获取encryptData域
		String encryptData = realtimePaymentBean.getEncryptData();
		if (StringUtils.isBlank(encryptData)) {
			return new ResultBean("BP？？？？", "加密信息域不能为空！");
		}
		JSONObject jo = JSONObject.fromObject(encryptData);
		EncryptDataBean edb = (EncryptDataBean) JSONObject.toBean(jo, EncryptDataBean.class);

		// 获取合同号
		String debtorConsign = edb.getDebtorConsign();
		ContractBean contractBean = contractDAO.queryContractByNum(debtorConsign);
		// 检查代收付账户信息是否和合同中匹配
		if (edb.getDebtorName().equals(contractBean.getDebtorName())
				&& edb.getDebtorAccount().equals(contractBean.getDebtorAccountNo())
				&& edb.getDebtorBank().equals(contractBean.getDebtorAccountNo())) {
			// TODO:代收业务
		} else {
			return new ResultBean("BP？？？？", "合同信息有误！");
		}
		return null;
	}

}
