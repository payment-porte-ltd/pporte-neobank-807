package com.pporte.model;

public class LoyaltyRules {
	
	private String sequenceId;
	private String payMode;
	private String ruleDesc;
	private String pointsConvertRatio;
	private String cryptoConvertRatio;
	private String userType;
	private String status;
	private String createdon;
	
	public String getSequenceId() {
		return sequenceId;
	}
	public String getPayMode() {
		return payMode;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public String getPointsConvertRatio() {
		return pointsConvertRatio;
	}
	public String getCryptoConvertRatio() {
		return cryptoConvertRatio;
	}
	public String getUserType() {
		return userType;
	}
	public String getStatus() {
		return status;
	}
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public void setPointsConvertRatio(String pointsConvertRatio) {
		this.pointsConvertRatio = pointsConvertRatio;
	}
	public void setCryptoConvertRatio(String cryptoConvertRatio) {
		this.cryptoConvertRatio = cryptoConvertRatio;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedon() {
		return createdon;
	}
	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}

}
