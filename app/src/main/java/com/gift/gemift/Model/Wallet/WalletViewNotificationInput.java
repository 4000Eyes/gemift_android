package com.gift.gemift.Model.Wallet;

import com.google.gson.annotations.SerializedName;

public class WalletViewNotificationInput {

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("request_type")
	private String requestType;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("type_id")
	private String typeId;

	@SerializedName("api_call_time")
	private String apiCallTime;

	@SerializedName("time_zone")
	private String timeZone;

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setRequestType(String requestType){
		this.requestType = requestType;
	}

	public String getRequestType(){
		return requestType;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setApiCallTime(String apiCallTime){
		this.apiCallTime = apiCallTime;
	}

	public String getApiCallTime(){
		return apiCallTime;
	}

	public void setTimeZone(String timeZone){
		this.timeZone = timeZone;
	}

	public String getTimeZone(){
		return timeZone;
	}
}