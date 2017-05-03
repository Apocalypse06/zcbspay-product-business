package com.zcbspay.platform.business.api.auth.bean;

import java.io.Serializable;

public class MerchApiAuthBean implements Serializable {

	private static final long serialVersionUID = 5846762465219445015L;
	/** 标示 **/
	private long tid;
	/** 会员号 **/
	private String memberid;
	/** 产品类型 **/
	private String biztype;
	/** 交易类型 **/
	private String txntype;
	/** 交易子类 **/
	private String txnsubtype;
	/** API接口名称 **/
	private String apiname;
	/** 状态 00-有此交易权限，其他-没有此交易权限 **/
	private String status;
	/** 备注1 **/
	private String notes;
	/** 备注2 **/
	private String remarks;

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getBiztype() {
		return biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

	public String getTxntype() {
		return txntype;
	}

	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}

	public String getTxnsubtype() {
		return txnsubtype;
	}

	public void setTxnsubtype(String txnsubtype) {
		this.txnsubtype = txnsubtype;
	}

	public String getApiname() {
		return apiname;
	}

	public void setApiname(String apiname) {
		this.apiname = apiname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
