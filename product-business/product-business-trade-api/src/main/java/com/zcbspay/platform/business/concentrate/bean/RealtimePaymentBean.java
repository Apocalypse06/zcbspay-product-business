package com.zcbspay.platform.business.concentrate.bean;

import java.io.Serializable;

public class RealtimePaymentBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8440617915484603783L;
	
	private String	version	;//	版本
	private String	encoding	;//	编码方式
	private String	txnType	;//	交易类型
	private String	txnSubType	;//	交易子类
	private String	bizType	;//	产品类型
	private String	txnTime	;//	订单发送时间
	private String	merId	;//	商户代码
	private String	merName	;//	商户全称
	private String	merAbbr	;//	商户简称
	private String	orderId	;//	商户订单号
	private String	currencyCode	;//	交易币种
	private String	txnAmt	;//	金额
	private String	encryptData	;//	加密信息域
	private String	reserved	;//	保留域
	private String	respCode	;//	响应码
	private String	respMsg	;//	应答信息
	private String	tn	;//	受理订单号
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnSubType() {
		return txnSubType;
	}
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getMerAbbr() {
		return merAbbr;
	}
	public void setMerAbbr(String merAbbr) {
		this.merAbbr = merAbbr;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getEncryptData() {
		return encryptData;
	}
	public void setEncryptData(String encryptData) {
		this.encryptData = encryptData;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
}
