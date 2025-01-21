package com.pporte.model;

public class PricingSlabRates {
	
	private String sequenceId;
	private String planId;
	private String fromRange;
	private String toRange;
	private String rate;
	private String status;
	
	public String getSequenceId() {
		return sequenceId;
	}
	public String getPlanId() {
		return planId;
	}
	public String getFromRange() {
		return fromRange;
	}
	public String getToRange() {
		return toRange;
	}
	public String getRate() {
		return rate;
	}
	public String getStatus() {
		return status;
	}
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public void setFromRange(String fromRange) {
		this.fromRange = fromRange;
	}
	public void setToRange(String toRange) {
		this.toRange = toRange;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
