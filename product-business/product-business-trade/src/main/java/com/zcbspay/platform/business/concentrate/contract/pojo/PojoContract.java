package com.zcbspay.platform.business.concentrate.contract.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CONTRACT")
public class PojoContract implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 8756020018422415370L;

	// 标识
	private Long TID;
	// 合同编号
	private String contractNum;
	// 商户号
	private String merchNo;
	// 付款人名称
	private String debtorName;
	// 付款人账号
	private String debtorAccountNo;
	// 付款行行号
	private String debtorBranchCode;
	// 收款人名称
	private String creditorName;
	// 收款人账号
	private String creditorAccountNo;
	// 收款行行号
	private String creditorBranchCode;
	// 合同收付类型 CT00：代收协议 CT01：代付协议 CT99：代收付协议
	private String contractType;
	// 单笔金额上限(分)-付款
	private Long debtorAmountLimit;
	// 付款累计金额限制类型00 不限 01 按年限次 02 按月限次03 总计限次
	private String debtorTransAmtLimitType;
	// 累计金额上限(分)-付款
	private Long debtorAccuAmountLimit;
	// 付款次数限制类型00 不限 01 按年限次 02 按月限次03 总计限次
	private String debtorTransNumLimitType;
	// 付款次数限制
	private Long debtorTransLimit;
	// 单笔金额上限(分) -收款
	private Long creditorAmountLimit;
	// 收款累计金额限制类型00 不限 01 按年限次 02 按月限次03 总计限次
	private String creditorTransAmtLimitType;
	// 累计金额上限(分) -收款
	private Long creditorAccuAmountLimit;
	// 收款次数限制类型00 不限 01 按年限次 02 按月限次03 总计限次
	private String creditorTransNumLimitType;
	// 付款次数限制
	private Long creditorTransLimit;
	// 签约日期
	private String signDate;
	// 失效日期
	private String expiryDate;
	// 00 有效 10 待审 19审核未果 89过期失效99撤销
	private String status;
	// 写入人
	private Long inuser;
	// 写入时间
	private Date inTime;
	// 初审人
	private Long stexaUser;
	// 初审时间
	private Date stexaTime;
	// 初审意见
	private String stexaOpt;
	// 复核人
	private Long cvlexaUser;
	// 复核时间
	private Date cvlexaTime;
	// 复核意见
	private String cvlexaOpt;
	// 备注
	private String notes;
	// 备注
	private String remarks;

	/** default constructor */
	public PojoContract() {
	};

	@Id
	public Long getTID() {
		return TID;
	}

	public void setTID(Long tID) {
		TID = tID;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getMerchNo() {
		return merchNo;
	}

	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}

	public String getDebtorName() {
		return debtorName;
	}

	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	public String getDebtorAccountNo() {
		return debtorAccountNo;
	}

	public void setDebtorAccountNo(String debtorAccountNo) {
		this.debtorAccountNo = debtorAccountNo;
	}

	public String getDebtorBranchCode() {
		return debtorBranchCode;
	}

	public void setDebtorBranchCode(String debtorBranchCode) {
		this.debtorBranchCode = debtorBranchCode;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorAccountNo() {
		return creditorAccountNo;
	}

	public void setCreditorAccountNo(String creditorAccountNo) {
		this.creditorAccountNo = creditorAccountNo;
	}

	public String getCreditorBranchCode() {
		return creditorBranchCode;
	}

	public void setCreditorBranchCode(String creditorBranchCode) {
		this.creditorBranchCode = creditorBranchCode;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Long getDebtorAmountLimit() {
		return debtorAmountLimit;
	}

	public void setDebtorAmountLimit(Long debtorAmountLimit) {
		this.debtorAmountLimit = debtorAmountLimit;
	}

	public String getDebtorTransAmtLimitType() {
		return debtorTransAmtLimitType;
	}

	public void setDebtorTransAmtLimitType(String debtorTransAmtLimitType) {
		this.debtorTransAmtLimitType = debtorTransAmtLimitType;
	}

	public Long getDebtorAccuAmountLimit() {
		return debtorAccuAmountLimit;
	}

	public void setDebtorAccuAmountLimit(Long debtorAccuAmountLimit) {
		this.debtorAccuAmountLimit = debtorAccuAmountLimit;
	}

	public String getDebtorTransNumLimitType() {
		return debtorTransNumLimitType;
	}

	public void setDebtorTransNumLimitType(String debtorTransNumLimitType) {
		this.debtorTransNumLimitType = debtorTransNumLimitType;
	}

	public Long getDebtorTransLimit() {
		return debtorTransLimit;
	}

	public void setDebtorTransLimit(Long debtorTransLimit) {
		this.debtorTransLimit = debtorTransLimit;
	}

	public Long getCreditorAmountLimit() {
		return creditorAmountLimit;
	}

	public void setCreditorAmountLimit(Long creditorAmountLimit) {
		this.creditorAmountLimit = creditorAmountLimit;
	}

	public String getCreditorTransAmtLimitType() {
		return creditorTransAmtLimitType;
	}

	public void setCreditorTransAmtLimitType(String creditorTransAmtLimitType) {
		this.creditorTransAmtLimitType = creditorTransAmtLimitType;
	}

	public Long getCreditorAccuAmountLimit() {
		return creditorAccuAmountLimit;
	}

	public void setCreditorAccuAmountLimit(Long creditorAccuAmountLimit) {
		this.creditorAccuAmountLimit = creditorAccuAmountLimit;
	}

	public String getCreditorTransNumLimitType() {
		return creditorTransNumLimitType;
	}

	public void setCreditorTransNumLimitType(String creditorTransNumLimitType) {
		this.creditorTransNumLimitType = creditorTransNumLimitType;
	}

	public Long getCreditorTransLimit() {
		return creditorTransLimit;
	}

	public void setCreditorTransLimit(Long creditorTransLimit) {
		this.creditorTransLimit = creditorTransLimit;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getInuser() {
		return inuser;
	}

	public void setInuser(Long inuser) {
		this.inuser = inuser;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Long getStexaUser() {
		return stexaUser;
	}

	public void setStexaUser(Long stexaUser) {
		this.stexaUser = stexaUser;
	}

	public Date getStexaTime() {
		return stexaTime;
	}

	public void setStexaTime(Date stexaTime) {
		this.stexaTime = stexaTime;
	}

	public String getStexaOpt() {
		return stexaOpt;
	}

	public void setStexaOpt(String stexaOpt) {
		this.stexaOpt = stexaOpt;
	}

	public Long getCvlexaUser() {
		return cvlexaUser;
	}

	public void setCvlexaUser(Long cvlexaUser) {
		this.cvlexaUser = cvlexaUser;
	}

	public Date getCvlexaTime() {
		return cvlexaTime;
	}

	public void setCvlexaTime(Date cvlexaTime) {
		this.cvlexaTime = cvlexaTime;
	}

	public String getCvlexaOpt() {
		return cvlexaOpt;
	}

	public void setCvlexaOpt(String cvlexaOpt) {
		this.cvlexaOpt = cvlexaOpt;
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
