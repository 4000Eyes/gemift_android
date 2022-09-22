package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class UpdateOccasion{

	@SerializedName("occasion_id")
	private String occasionId;

	@SerializedName("value_timezone")
	private String valueTimezone;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("occasion_date")
	private String occasionDate;

	@SerializedName("friend_circle_id")
	private String friendCircleId;

	@SerializedName("api_call_time")
	private String apiCallTime;

	@SerializedName("time_zone")
	private String timeZone;

	@SerializedName("request_id")
	private int requestId;

	public void setOccasionId(String occasionId){
		this.occasionId = occasionId;
	}

	public String getOccasionId(){
		return occasionId;
	}

	public void setValueTimezone(String valueTimezone){
		this.valueTimezone = valueTimezone;
	}

	public String getValueTimezone(){
		return valueTimezone;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setOccasionDate(String occasionDate){
		this.occasionDate = occasionDate;
	}

	public String getOccasionDate(){
		return occasionDate;
	}

	public void setFriendCircleId(String friendCircleId){
		this.friendCircleId = friendCircleId;
	}

	public String getFriendCircleId(){
		return friendCircleId;
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

	public void setRequestId(int requestId){
		this.requestId = requestId;
	}

	public int getRequestId(){
		return requestId;
	}

	@Override
 	public String toString(){
		return 
			"UpdateOccasion{" + 
			"occasion_id = '" + occasionId + '\'' + 
			",value_timezone = '" + valueTimezone + '\'' + 
			",user_id = '" + userId + '\'' + 
			",occasion_date = '" + occasionDate + '\'' + 
			",friend_circle_id = '" + friendCircleId + '\'' + 
			",api_call_time = '" + apiCallTime + '\'' + 
			",time_zone = '" + timeZone + '\'' + 
			",request_id = '" + requestId + '\'' + 
			"}";
		}
}