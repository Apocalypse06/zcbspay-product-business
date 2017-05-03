package com.zcbspay.platform.business.merch.bean;

import java.util.List;

/**
 * 批量导入请求bean
 * 
 * @author: zhangshd
 * @date: 2017年3月13日 下午1:17:46
 * @version :v1.0
 */
public class BatchImportReqBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5308359541722139667L;

	private String merId;// 商户代码
	private String batchNo;// 批次号
	private String txnTime;// 订单发送时间
	private List<BatchImportFileContent> fileContents;// 文件内容

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public List<BatchImportFileContent> getFileContents() {
		return fileContents;
	}

	public void setFileContents(List<BatchImportFileContent> fileContents) {
		this.fileContents = fileContents;
	}

}
