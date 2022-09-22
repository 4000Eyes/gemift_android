package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class ProductVote{

	@SerializedName("occasion_id")
	private String occasionId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("occasion_year")
	private int occasionYear;

	@SerializedName("comment")
	private String comment;

	@SerializedName("friend_circle_id")
	private String friendCircleId;

	@SerializedName("occasion_name")
	private String occasionName;

	@SerializedName("api_call_time")
	private String apiCallTime;

	@SerializedName("time_zone")
	private String timeZone;

	@SerializedName("request_id")
	private int requestId;

	@SerializedName("vote")
	private int vote;

	public void setOccasionId(String occasionId){
		this.occasionId = occasionId;
	}

	public String getOccasionId(){
		return occasionId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setOccasionYear(int occasionYear){
		this.occasionYear = occasionYear;
	}

	public int getOccasionYear(){
		return occasionYear;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
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

	public void setVote(int vote){
		this.vote = vote;
	}

	public int getVote(){
		return vote;
	}

	@Override
 	public String toString(){
		return 
			"ProductVote{" + 
			"occasion_id = '" + occasionId + '\'' + 
			",user_id = '" + userId + '\'' + 
			",product_id = '" + productId + '\'' + 
			",occasion_year = '" + occasionYear + '\'' + 
			",comment = '" + comment + '\'' + 
			",friend_circle_id = '" + friendCircleId + '\'' + 
			",occasion_name = '" + occasionName + '\'' + 
			",api_call_time = '" + apiCallTime + '\'' + 
			",time_zone = '" + timeZone + '\'' + 
			",request_id = '" + requestId + '\'' + 
			",vote = '" + vote + '\'' + 
			"}";
		}
}