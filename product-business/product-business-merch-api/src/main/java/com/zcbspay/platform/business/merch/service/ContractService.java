package com.zcbspay.platform.business.merch.service;

import com.zcbspay.platform.business.merch.bean.BatchImportReqBean;
import com.zcbspay.platform.business.merch.bean.ResultBean;

public interface ContractService {
	/**
	 * 查询合同详情
	 * @param contractNum 合同编号
	 * @return
	 */
	ResultBean findByCode(String contractNum);
	
	/**
	 * 合同批量导入保存
	 * @param batchImportResBean 批量导入请求bean
	 * @return
	 */
	ResultBean importBatchContract(BatchImportReqBean batchImportReqBean);
}
