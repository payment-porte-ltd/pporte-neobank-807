package com.pporte.model;

public class AssetOffer {
	private String accountId;
	private String sellAsset;
	private String buyAsset;
	private String date;
	private String price;
	private String id;
	private String amount;
	public String getAccountId() {
		return accountId;
	}
	public String getSellAsset() {
		return sellAsset;
	}
	public String getBuyAsset() {
		return buyAsset;
	}
	public String getDate() {
		return date;
	}
	public String getPrice() {
		return price;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setSellAsset(String sellAsset) {
		this.sellAsset = sellAsset;
	}
	public void setBuyAsset(String buyAsset) {
		this.buyAsset = buyAsset;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}


}
