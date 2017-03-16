package com.zcbspay.platform.business.concentrate.batch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.business.concentrate.batch.service.BatchCollection;
import com.zcbspay.platform.business.concentrate.bean.BatchCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.FileContentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;

@Service
public class BatchCollectionImpl implements BatchCollection {
	@Autowired
	private ContractDAO contractDAO;

	@Override
	public ResultBean pay(BatchCollectionBean batchCollectionBean) {
		List<FileContentBean> fcbs = new ArrayList<>();

		if (batchCollectionBean == null || batchCollectionBean.getFileContent() == null) {
			return new ResultBean("BP0000", "参数不能为空！");
		}

		// 遍历文件域
		fcbs = batchCollectionBean.getFileContent();
		for (FileContentBean fcb : fcbs) {
			ContractBean contractBean = contractDAO.queryContractByNum(fcb.getDebtorConsign());
			// 检查代收付账户信息是否和合同中匹配
			if (fcb.getCreditorBank().equals(contractBean.getCreditorBranchCode())
					&& fcb.getCreditorName().equals(contractBean.getCreditorName())
					&& fcb.getCreditorAccount().equals(contractBean.getCreditorAccountNo())
					&& fcb.getDebtorName().equals(contractBean.getDebtorName())
					&& fcb.getDebtorAccount().equals(contractBean.getDebtorAccountNo())
					&& fcb.getDebtorBank().equals(contractBean.getDebtorAccountNo())) {
				// TODO:代收业务
			} else {
				return new ResultBean("BP？？？？", "合同信息有误！");
			}
		}

		return null;
	}

}
