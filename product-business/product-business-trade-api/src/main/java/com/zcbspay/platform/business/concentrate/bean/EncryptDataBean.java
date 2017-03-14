package com.zcbspay.platform.business.concentrate.bean;

import java.io.Serializable;

public class EncryptDataBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1972018568949174322L;
	private String 	debtorBank	;//	付款人银行号
	private String 	debtorAccount	;//	付款人账号
	private String 	debtorName	;//	付款人名称
	private String 	debtorConsign	;//	付款合同号
	private String 	creditorBank	;//	收款人银行号
	private String 	creditorAccount	;//	收款人账号
	private String 	creditorName	;//	收款人名称
	private String 	proprietary	;//	业务种类编码
	private String 	summary	;//	摘要
	public String getDebtorBank() {
		return debtorBank;
	}
	public void setDebtorBank(String debtorBank) {
		this.debtorBank = debtorBank;
	}
	public String getDebtorAccount() {
		return debtorAccount;
	}
	public void setDebtorAccount(String debtorAccount) {
		this.debtorAccount = debtorAccount;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getDebtorConsign() {
		return debtorConsign;
	}
	public void setDebtorConsign(String debtorConsign) {
		this.debtorConsign = debtorConsign;
	}
	public String getCreditorBank() {
		return creditorBank;
	}
	public void setCreditorBank(String creditorBank) {
		this.creditorBank = creditorBank;
	}
	public String getCreditorAccount() {
		return creditorAccount;
	}
	public void setCreditorAccount(String creditorAccount) {
		this.creditorAccount = creditorAccount;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getProprietary() {
		return proprietary;
	}
	public void setProprietary(String proprietary) {
		this.proprietary = proprietary;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
