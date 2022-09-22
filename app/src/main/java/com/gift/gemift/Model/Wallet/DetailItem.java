package com.gift.gemift.Model.Wallet;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailItem implements Serializable {

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("transaction_status")
	private int transactionStatus;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("query_type")
	private Object queryType;

	@SerializedName("per_user_share")
	private double perUserShare;

	@SerializedName("opt_in_flag")
	private String optInFlag;

	@SerializedName("email_address")
	private String emailAddress;

	@SerializedName("user_type")
	private String userType;

	@SerializedName("recommended_share")
	private String  recommendedShare;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("opt_in_date")
	private String optInDate;

	@SerializedName("paid_amount")
	private int paidAmount;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("first_name")
	private String firstName;

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

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setQueryType(Object queryType){
		this.queryType = queryType;
	}

	public Object getQueryType(){
		return queryType;
	}

	public void setPerUserShare(double perUserShare){
		this.perUserShare = perUserShare;
	}

	public double getPerUserShare(){
		return perUserShare;
	}

	public void setOptInFlag(String optInFlag){
		this.optInFlag = optInFlag;
	}

	public String getOptInFlag(){
		return optInFlag;
	}

	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public void setUserType(String userType){
		this.userType = userType;
	}

	public String getUserType(){
		return userType;
	}


	public String getRecommendedShare() {
		return recommendedShare;
	}

	public void setRecommendedShare(String recommendedShare) {
		this.recommendedShare = recommendedShare;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setOptInDate(String optInDate){
		this.optInDate = optInDate;
	}

	public String getOptInDate(){
		return optInDate;
	}

	public void setPaidAmount(int paidAmount){
		this.paidAmount = paidAmount;
	}

	public int getPaidAmount(){
		return paidAmount;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	@Override
 	public String toString(){
		return 
			"DetailItem{" + 
			"transaction_id = '" + transactionId + '\'' + 
			",transaction_status = '" + transactionStatus + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",query_type = '" + queryType + '\'' + 
			",per_user_share = '" + perUserShare + '\'' + 
			",opt_in_flag = '" + optInFlag + '\'' + 
			",email_address = '" + emailAddress + '\'' + 
			",user_type = '" + userType + '\'' + 
			",recommended_share = '" + recommendedShare + '\'' + 
			",user_id = '" + userId + '\'' + 
			",opt_in_date = '" + optInDate + '\'' + 
			",paid_amount = '" + paidAmount + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",first_name = '" + firstName + '\'' + 
			"}";
		}
}