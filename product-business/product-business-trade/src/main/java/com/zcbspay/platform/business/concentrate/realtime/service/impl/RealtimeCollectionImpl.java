package com.zcbspay.platform.business.concentrate.realtime.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.EncryptDataBean;
import com.zcbspay.platform.business.concentrate.bean.RealtimeCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.realtime.service.RealtimeCollection;

import net.sf.json.JSONObject;

@Service
public class RealtimeCollectionImpl implements RealtimeCollection {
	@Autowired
	private ContractDAO contractDAO;

	@Override
	public ResultBean pay(RealtimeCollectionBean realtimeCollectionBean) {
		if (realtimeCollectionBean == null) {
			return new ResultBean("BP0000", "参数不能为空！");
		}
		
		// 获取encryptData域
		String encryptData = realtimeCollectionBean.getEncryptData();
		if (StringUtils.isBlank(encryptData)) {
			return new ResultBean("BP？？？？", "加密信息域不能为空！");
		}
		JSONObject jo = JSONObject.fromObject(encryptData);
		EncryptDataBean edb = (EncryptDataBean) JSONObject.toBean(jo, EncryptDataBean.class);
		
		// 获取合同号
		String debtorConsign = edb.getDebtorConsign();
		ContractBean contractBean = contractDAO.queryContractByNum(debtorConsign);
		// 检查代收付账户信息是否和合同中匹配
		if (edb.getCreditorBank().equals(contractBean.getCreditorBranchCode()) && edb.getCreditorName().equals(contractBean.getCreditorName()) && edb.getCreditorAccount().equals(contractBean.getCreditorAccountNo())) {
			// TODO:代收业务
		}else {
			return new ResultBean("BP？？？？", "合同信息有误！");
		}
		return null;
		
	}

}
