package com.pporte.model;

public class Loyalty {
	String sequenceId;
	String walletId;
	String userType;
	String payMode;
	String txnReference;
	String pointAccrued;
	String pointBalance;
	String txnDate;
	String status;
	String relationshipNo;
	String userId;
	String ruleDesc;
	String conversionRate;
	String destinationAsset;
	String createdon;
	String expiry;
	
	public String getSequenceId() {
		return sequenceId;
	}
	public String getWalletId() {
		return walletId;
	}
	public String getUserType() {
		return userType;
	}
	public String getPayMode() {
		return payMode;
	}
	public String getTxnReference() {
		return txnReference;
	}
	public String getPointAccrued() {
		return pointAccrued;
	}
	public String getPointBalance() {
		return pointBalance;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public String getStatus() {
		return status;
	}
	public String getRelationshipNo() {
		return relationshipNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public void setTxnReference(String txnReference) {
		this.txnReference = txnReference;
	}
	public void setPointAccrued(String pointAccrued) {
		this.pointAccrued = pointAccrued;
	}
	public void setPointBalance(String pointBalance) {
		this.pointBalance = pointBalance;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setRelationshipNo(String relationshipNo) {
		this.relationshipNo = relationshipNo;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	
	public String getConversionRate() {
		return conversionRate;
	}
	public String getDestinationAsset() {
		return destinationAsset;
	}
	
	public void setConversionRate(String conversionRate) {
		this.conversionRate = conversionRate;
	}
	public void setDestinationAsset(String destinationAsset) {
		this.destinationAsset = destinationAsset;
	}
	public String getCreatedon() {
		return createdon;
	}
	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

}
