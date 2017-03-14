package com.zcbspay.platform.business.concentrate.batch.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.business.concentrate.batch.service.BatchCollection;
import com.zcbspay.platform.business.concentrate.bean.BatchCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.EncryptDataBean;
import com.zcbspay.platform.business.concentrate.bean.FileContentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;

import net.sf.json.JSONObject;

@Service
public class BatchCollectionImpl implements BatchCollection {
	@Autowired
	private ContractDAO contractDAO;

	@Override
	public ResultBean pay(BatchCollectionBean batchCollectionBean) {
		if (batchCollectionBean == null) {
			return new ResultBean("BP0000", "参数不能为空！");
		}

		// 获取encryptData域
		String fileContent = batchCollectionBean.getFileContent();
		if (StringUtils.isBlank(fileContent)) {
			return new ResultBean("BP？？？？", "文件内容不能为空！");
		}
		JSONObject jo = JSONObject.fromObject(fileContent);
		FileContentBean fcb = (FileContentBean) JSONObject.toBean(jo, EncryptDataBean.class);

		// 获取合同号
		String debtorConsign = fcb.getDebtorConsign();
		ContractBean contractBean = contractDAO.queryContractByNum(debtorConsign);
		// 检查代收付账户信息是否和合同中匹配
		if (fcb.getCreditorBank().equals(contractBean.getCreditorBranchCode())
				&& fcb.getCreditorName().equals(contractBean.getCreditorName())
				&& fcb.getCreditorAccount().equals(contractBean.getCreditorAccountNo())) {
			// TODO:代收业务
		} else {
			return new ResultBean("BP？？？？", "合同信息有误！");
		}
		return null;

	}

}
