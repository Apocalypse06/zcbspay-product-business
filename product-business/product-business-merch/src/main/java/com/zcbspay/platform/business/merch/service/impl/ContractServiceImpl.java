package com.zcbspay.platform.business.merch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.merch.bean.BatchImportFileContent;
import com.zcbspay.platform.business.merch.bean.BatchImportReqBean;
import com.zcbspay.platform.business.merch.bean.ContractQueryFileContent;
import com.zcbspay.platform.business.merch.bean.ResultBean;
import com.zcbspay.platform.business.merch.service.ContractBizService;
import com.zcbspay.platform.manager.merchant.bean.ContractBean;

@Service("contractBizService")
@Transactional
public class ContractServiceImpl implements ContractBizService {
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

	@Autowired
	private com.zcbspay.platform.manager.merchant.service.ContractService contractService;

	@Override
	public ResultBean findByCode(String contractNum) {
		if (StringUtils.isBlank(contractNum)) {
			logger.info("合同编号不能为空!");
			return new ResultBean("BC003", "合同编号不能为空!");
		}
		try {
			ContractBean contractBean = contractService.findByCode(contractNum);
			ContractQueryFileContent cqfc = new ContractQueryFileContent();
			cqfc.setContractnum(contractBean.getContractNum());
			cqfc.setContracttype(contractBean.getContractType());
			cqfc.setDebtorname(contractBean.getDebName());
			cqfc.setDebtoraccountno(contractBean.getDebAccNo());
			cqfc.setDebtorbranchcode(contractBean.getDebBranchCode());
			cqfc.setDebtoramountlimit(contractBean.getDebAmoLimit().toString());
			cqfc.setDebtortransamtlimittype(contractBean.getDebTransLimitType());
			cqfc.setDebtoraccuamountlimit(contractBean.getDebAccyAmoLimit().toString());
			cqfc.setDebtortransnumlimittype(contractBean.getDebTransLimitType());
			cqfc.setDebtortranslimit(contractBean.getDebTransLimit().toString());
			cqfc.setCreditorname(contractBean.getCredName());
			cqfc.setCreditoraccountno(contractBean.getCredAccNo());
			cqfc.setCreditoramountlimit(contractBean.getCredBranchCode());
			cqfc.setCreditoramountlimit(contractBean.getCredAmoLimit().toString());
			cqfc.setCreditortransamtlimittype(contractBean.getCredTranLimitType());
			cqfc.setCreditoraccuamountlimit(contractBean.getCredAccuAmoLimit().toString());
			cqfc.setCreditortransnumlimittype(contractBean.getCredTransLimitType());
			cqfc.setCreditortranslimit(contractBean.getCredTransLimit().toString());
			cqfc.setSigndate(contractBean.getSignDate());
			cqfc.setExpirydate(contractBean.getExpiryDate());
			cqfc.setFileaddress(contractBean.getFileAddress());
			cqfc.setCategorypurpose(contractBean.getCategoryPurpose());
			cqfc.setProprietary(contractBean.getProprieTary());
			cqfc.setStatus(contractBean.getStatus());
			return new ResultBean(cqfc);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("合同服务异常!");
			return new ResultBean("BC004", "合同服务异常!");
		}
	}

	@Override
	public ResultBean importBatchContract(BatchImportReqBean batchImportReqBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ContractBean> contractBeans = new ArrayList<>();
		
		for (BatchImportFileContent bifc : batchImportReqBean.getFileContents()) {
			ContractBean contractBean = new ContractBean();
			try {
				contractBean.setMerchNo(batchImportReqBean.getMerId());
				contractBean.setInUser(Long.valueOf(3));
				contractBean.setContractNum(bifc.getContractnum());
				contractBean.setContractType(bifc.getContracttype());
				contractBean.setDebName(bifc.getDebtorname());
				contractBean.setDebAccNo(bifc.getDebtoraccountno());
				contractBean.setDebBranchCode(bifc.getDebtorbranchcode());
				contractBean.setDebAmoLimit(bifc.getDebtoramountlimit());
				contractBean.setDebTransLimitType(bifc.getDebtortransamtlimittype());
				contractBean.setDebAccyAmoLimit(bifc.getDebtoraccuamountlimit());
				contractBean.setDebTransLimitType(bifc.getDebtortransnumlimittype());
				contractBean.setDebTransLimit(Long.valueOf(bifc.getDebtortranslimit()));
				contractBean.setCredName(bifc.getCreditorname());
				contractBean.setCredAccNo(bifc.getCreditoraccountno());
				contractBean.setCredBranchCode(bifc.getCreditorbranchcode());
				contractBean.setCredAmoLimit(bifc.getCreditoramountlimit());
				contractBean.setCredTranLimitType(bifc.getCreditortransamtlimittype());
				contractBean.setCredAccuAmoLimit(bifc.getCreditoraccuamountlimit());
				contractBean.setCredTransLimitType(bifc.getCreditortransnumlimittype());
				contractBean.setCredTransLimit(Long.valueOf(bifc.getCreditortranslimit()));
				contractBean.setSignDate(bifc.getSigndate());
				contractBean.setExpiryDate(bifc.getExpirydate());
				contractBean.setFileAddress(bifc.getFileaddress());
				contractBean.setCategoryPurpose(bifc.getCategorypurpose());
				contractBean.setProprieTary(bifc.getProprietary());
				
				contractBeans.add(contractBean);
			} catch (Exception e) {
				logger.info("合同信息有误!");
				e.printStackTrace();
				return new ResultBean("BC005", "合同信息有误!");
			}
		}
		
		try {
			result = contractService.importBatch(contractBeans, batchImportReqBean.getBatchNo(), batchImportReqBean.getMerId());
			String ret = result.get("RET").toString();
			if (!ret.equals("succ")) {
				logger.info(result.get("INFO").toString());
				return new ResultBean("BC006", result.get("INFO").toString());
			}
			return new ResultBean("导入成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("合同服务异常!");
			return new ResultBean("BC004", "合同服务异常!");
		}
	}

}
