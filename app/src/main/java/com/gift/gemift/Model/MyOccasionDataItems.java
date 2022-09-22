package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class MyOccasionDataItems {

	@SerializedName("occasion_id")
	private String occasionId;

	@SerializedName("occasion_date")
	private String occasionDate;

	@SerializedName("days_left")
	private int daysLeft;

	@SerializedName("product_count")
	private int productCount;

	@SerializedName("secret_friend_last_name")
	private Object secretFriendLastName;

	@SerializedName("occasion_frequency")
	private String occasionFrequency;

	@SerializedName("occasion_active_status")
	private String occasionActiveStatus;

	@SerializedName("friend_circle_id")
	private String friend_circle_id;

	@SerializedName("occasion_name")
	private String occasionName;

	@SerializedName("next_occasion_date")
	private String nextOccasionDate;

	@SerializedName("message")
	private String message;

	@SerializedName("friend_occasion_status")
	private int friendOccasionStatus;

	@SerializedName("secret_friend_first_name")
	private String secretFriendFirstName;

	public void setOccasionId(String occasionId){
		this.occasionId = occasionId;
	}

	public String getOccasionId(){
		return occasionId;
	}

	public void setOccasionDate(String occasionDate){
		this.occasionDate = occasionDate;
	}

	public String getOccasionDate(){
		return occasionDate;
	}

	public void setDaysLeft(int daysLeft){
		this.daysLeft = daysLeft;
	}

	public int getDaysLeft(){
		return daysLeft;
	}

	public void setProductCount(int productCount){
		this.productCount = productCount;
	}

	public int getProductCount(){
		return productCount;
	}

	public void setSecretFriendLastName(Object secretFriendLastName){
		this.secretFriendLastName = secretFriendLastName;
	}

	public Object getSecretFriendLastName(){
		return secretFriendLastName;
	}

	public void setOccasionFrequency(String occasionFrequency){
		this.occasionFrequency = occasionFrequency;
	}

	public String getOccasionFrequency(){
		return occasionFrequency;
	}

	public void setOccasionActiveStatus(String occasionActiveStatus){
		this.occasionActiveStatus = occasionActiveStatus;
	}

	public String getOccasionActiveStatus(){
		return occasionActiveStatus;
	}

	public void setOccasionName(String occasionName){
		this.occasionName = occasionName;
	}

	public String getOccasionName(){
		return occasionName;
	}

	public void setNextOccasionDate(String nextOccasionDate){
		this.nextOccasionDate = nextOccasionDate;
	}

	public String getFriend_circle_id() {
		return friend_circle_id;
	}

	public void setFriend_circle_id(String friend_circle_id) {
		this.friend_circle_id = friend_circle_id;
	}

	public String getNextOccasionDate(){
		return nextOccasionDate;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setFriendOccasionStatus(int friendOccasionStatus){
		this.friendOccasionStatus = friendOccasionStatus;
	}

	public int getFriendOccasionStatus(){
		return friendOccasionStatus;
	}

	public void setSecretFriendFirstName(String secretFriendFirstName){
		this.secretFriendFirstName = secretFriendFirstName;
	}

	public String getSecretFriendFirstName(){
		return secretFriendFirstName;
	}

	@Override
 	public String toString(){
		return 
			"OccasionsItem{" + 
			"occasion_id = '" + occasionId + '\'' + 
			",occasion_date = '" + occasionDate + '\'' + 
			",days_left = '" + daysLeft + '\'' + 
			",product_count = '" + productCount + '\'' + 
			",secret_friend_last_name = '" + secretFriendLastName + '\'' + 
			",occasion_frequency = '" + occasionFrequency + '\'' + 
			",occasion_active_status = '" + occasionActiveStatus + '\'' + 
			",occasion_name = '" + occasionName + '\'' + 
			",next_occasion_date = '" + nextOccasionDate + '\'' + 
			",message = '" + message + '\'' + 
			",friend_occasion_status = '" + friendOccasionStatus + '\'' + 
			",secret_friend_first_name = '" + secretFriendFirstName + '\'' + 
			"}";
		}
}