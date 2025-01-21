package com.pporte.model;

public class Risk {
	
 private String riskId;
 private String riskDesc;
 private String riskStatus;
 private String riskPaymentAction;
 private String createdon;
 
public String getRiskId() {
	return riskId;
}
public String getRiskDesc() {
	return riskDesc;
}
public String getRiskStatus() {
	return riskStatus;
}
public String getRiskPaymentAction() {
	return riskPaymentAction;
}
public String getCreatedon() {
	return createdon;
}
public void setRiskId(String riskId) {
	this.riskId = riskId;
}
public void setRiskDesc(String riskDesc) {
	this.riskDesc = riskDesc;
}
public void setRiskStatus(String riskStatus) {
	this.riskStatus = riskStatus;
}
public void setRiskPaymentAction(String riskPaymentAction) {
	this.riskPaymentAction = riskPaymentAction;
}
public void setCreatedon(String createdon) {
	this.createdon = createdon;
}
			
}
