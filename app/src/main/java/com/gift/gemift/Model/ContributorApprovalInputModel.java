package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class ContributorApprovalInputModel{

	@SerializedName("approval_flag")
	private Integer approvalFlag;

	@SerializedName("list_friend_circle_id")
	private String listFriendCircleId;

	@SerializedName("referred_user_id")
	private String referredUserId;

	@SerializedName("referrer_user_id")
	private String referrerUserId;

	@SerializedName("api_call_time")
	private String apiCallTime;

	@SerializedName("time_zone")
	private String timeZone;

	@SerializedName("request_id")
	private Integer requestId;

	public void setApprovalFlag(Integer approvalFlag){
		this.approvalFlag = approvalFlag;
	}

	public Integer getApprovalFlag(){
		return approvalFlag;
	}

	public void setListFriendCircleId(String listFriendCircleId){
		this.listFriendCircleId = listFriendCircleId;
	}

	public String getListFriendCircleId(){
		return listFriendCircleId;
	}

	public void setReferredUserId(String referredUserId){
		this.referredUserId = referredUserId;
	}

	public String getReferredUserId(){
		return referredUserId;
	}

	public void setReferrerUserId(String referrerUserId){
		this.referrerUserId = referrerUserId;
	}

	public String getReferrerUserId(){
		return referrerUserId;
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

	public void setRequestId(Integer requestId){
		this.requestId = requestId;
	}

	public Integer getRequestId(){
		return requestId;
	}
}