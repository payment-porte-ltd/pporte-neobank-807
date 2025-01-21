package com.pporte.model;

public class DisputeReasons {
	private String disputeReasonId;
	private String disputeReasonDesc;
	private String status;
	private String userType;
	private String payMode;
	
	public String getDisputeReasonId() {
		return disputeReasonId;
	}
	public void setDisputeReasonId(String disputeReasonId) {
		this.disputeReasonId = disputeReasonId;
	}
	public String getDisputeReasonDesc() {
		return disputeReasonDesc;
	}
	public void setDisputeReasonDesc(String disputeReasonDesc) {
		this.disputeReasonDesc = disputeReasonDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	
}
