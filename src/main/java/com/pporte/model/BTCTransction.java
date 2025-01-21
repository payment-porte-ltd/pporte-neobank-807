package com.pporte.model;

public class BTCTransction {
	private String hash;
	private int totalTxnAmount;
	private String txnFees;
	private String txnDateTime;
	private boolean doubleSpend ;
	private int confirmations ;
	private String destinatioAddress ;
	private String sourceAddress ;
	private String txnAmaount ;
	private String confirmedStatus ;
	private String txnMode ;
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getTotalTxnAmount() {
		return totalTxnAmount;
	}
	public void setTotalTxnAmount(int totalTxnAmount) {
		this.totalTxnAmount = totalTxnAmount;
	}
	public String getTxnFees() {
		return txnFees;
	}
	public void setTxnFees(String txnFees) {
		this.txnFees = txnFees;
	}
	public String getTxnDateTime() {
		return txnDateTime;
	}
	public void setTxnDateTime(String txnDateTime) {
		this.txnDateTime = txnDateTime;
	}
	public boolean isDoubleSpend() {
		return doubleSpend;
	}
	public void setDoubleSpend(boolean doubleSpend) {
		this.doubleSpend = doubleSpend;
	}
	public int getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}
	public String getDestinatioAddress() {
		return destinatioAddress;
	}
	public void setDestinatioAddress(String destinatioAddress) {
		this.destinatioAddress = destinatioAddress;
	}

	public String getTxnAmaount() {
		return txnAmaount;
	}
	public void setTxnAmaount(String txnAmaount) {
		this.txnAmaount = txnAmaount;
	}
	public String getConfirmedStatus() {
		return confirmedStatus;
	}
	public void setConfirmedStatus(String confirmedStatus) {
		this.confirmedStatus = confirmedStatus;
	}
	public String getTxnMode() {
		return txnMode;
	}
	public void setTxnMode(String txnMode) {
		this.txnMode = txnMode;
	}
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	
	
}
