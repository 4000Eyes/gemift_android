package com.gift.gemift.Model.Wallet;

import com.google.gson.annotations.SerializedName;

public class MessageItem{

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("transaction_status")
	private int transactionStatus;

	@SerializedName("total_members")
	private String totalMembers;

	@SerializedName("product_price")
	private String productPrice;

	@SerializedName("expiration_date")
	private String expirationDate;

	@SerializedName("per_user_share")
	private double perUserShare;

	@SerializedName("occasion_id")
	private String occasionId;

	@SerializedName("opt_in_flag")
	private String optInFlag;

	@SerializedName("user_type")
	private String userType;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("paid_amount")
	private String paidAmount;

	@SerializedName("initiated_date")
	private String initiatedDate;

	@SerializedName("friend_circle_id")
	private String friendCircleId;

	@SerializedName("occasion_name")
	private String occasionName;

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setTransactionStatus(int transactionStatus){
		this.transactionStatus = transactionStatus;
	}

	public int getTransactionStatus(){
		return transactionStatus;
	}

	public void setTotalMembers(String totalMembers){
		this.totalMembers = totalMembers;
	}

	public String getTotalMembers(){
		return totalMembers;
	}

	public void setProductPrice(String productPrice){
		this.productPrice = productPrice;
	}

	public String getProductPrice(){
		return productPrice;
	}

	public void setExpirationDate(String expirationDate){
		this.expirationDate = expirationDate;
	}

	public String getExpirationDate(){
		return expirationDate;
	}

	public void setPerUserShare(double perUserShare){
		this.perUserShare = perUserShare;
	}

	public double getPerUserShare(){
		return perUserShare;
	}

	public void setOccasionId(String occasionId){
		this.occasionId = occasionId;
	}

	public String getOccasionId(){
		return occasionId;
	}

	public void setOptInFlag(String optInFlag){
		this.optInFlag = optInFlag;
	}

	public String getOptInFlag(){
		return optInFlag;
	}

	public void setUserType(String userType){
		this.userType = userType;
	}

	public String getUserType(){
		return userType;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setPaidAmount(String paidAmount){
		this.paidAmount = paidAmount;
	}

	public String getPaidAmount(){
		return paidAmount;
	}

	public void setInitiatedDate(String initiatedDate){
		this.initiatedDate = initiatedDate;
	}

	public String getInitiatedDate(){
		return initiatedDate;
	}

	public void setFriendCircleId(String friendCircleId){
		this.friendCircleId = friendCircleId;
	}

	public String getFriendCircleId(){
		return friendCircleId;
	}

	public void setOccasionName(String occasionName){
		this.occasionName = occasionName;
	}

	public String getOccasionName(){
		return occasionName;
	}
}