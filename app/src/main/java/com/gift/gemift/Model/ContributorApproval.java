package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class ContributorApproval{

	@SerializedName("friend_circle_name")
	private String friendCircleName;

	@SerializedName("secret_first_name")
	private String secretFirstName;

	@SerializedName("image_url")
	private Object imageUrl;

	@SerializedName("friend_circle_creator_id")
	private String friendCircleCreatorId;

	@SerializedName("referrer_user_id")
	private String referrerUserId;

	@SerializedName("referred_user_id")
	private String referredUserId;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("secret_last_name")
	private String secretLastName;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("secret_friend_id")
	private String secretFriendId;

	@SerializedName("email_address")
	private String emailAddress;

	@SerializedName("friend_circle_creator_last_name")
	private String friendCircleCreatorLastName;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("friend_circle_id")
	private String friendCircleId;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("friend_circle_creator_first_name")
	private String friendCircleCreatorFirstName;

	public void setFriendCircleName(String friendCircleName){
		this.friendCircleName = friendCircleName;
	}

	public String getFriendCircleName(){
		return friendCircleName;
	}

	public void setSecretFirstName(String secretFirstName){
		this.secretFirstName = secretFirstName;
	}

	public String getSecretFirstName(){
		return secretFirstName;
	}

	public void setImageUrl(Object imageUrl){
		this.imageUrl = imageUrl;
	}

	public Object getImageUrl(){
		return imageUrl;
	}

	public void setFriendCircleCreatorId(String friendCircleCreatorId){
		this.friendCircleCreatorId = friendCircleCreatorId;
	}

	public String getFriendCircleCreatorId(){
		return friendCircleCreatorId;
	}

	public void setReferrerUserId(String referrerUserId){
		this.referrerUserId = referrerUserId;
	}

	public String getReferrerUserId(){
		return referrerUserId;
	}

	public void setReferredUserId(String referredUserId){
		this.referredUserId = referredUserId;
	}

	public String getReferredUserId(){
		return referredUserId;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setSecretLastName(String secretLastName){
		this.secretLastName = secretLastName;
	}

	public String getSecretLastName(){
		return secretLastName;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setSecretFriendId(String secretFriendId){
		this.secretFriendId = secretFriendId;
	}

	public String getSecretFriendId(){
		return secretFriendId;
	}

	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public void setFriendCircleCreatorLastName(String friendCircleCreatorLastName){
		this.friendCircleCreatorLastName = friendCircleCreatorLastName;
	}

	public String getFriendCircleCreatorLastName(){
		return friendCircleCreatorLastName;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setFriendCircleId(String friendCircleId){
		this.friendCircleId = friendCircleId;
	}

	public String getFriendCircleId(){
		return friendCircleId;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setFriendCircleCreatorFirstName(String friendCircleCreatorFirstName){
		this.friendCircleCreatorFirstName = friendCircleCreatorFirstName;
	}

	public String getFriendCircleCreatorFirstName(){
		return friendCircleCreatorFirstName;
	}

	@Override
 	public String toString(){
		return 
			"ContributorApproval{" + 
			"friend_circle_name = '" + friendCircleName + '\'' + 
			",secret_first_name = '" + secretFirstName + '\'' + 
			",image_url = '" + imageUrl + '\'' + 
			",friend_circle_creator_id = '" + friendCircleCreatorId + '\'' + 
			",referrer_user_id = '" + referrerUserId + '\'' + 
			",referred_user_id = '" + referredUserId + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",secret_last_name = '" + secretLastName + '\'' + 
			",country_code = '" + countryCode + '\'' + 
			",secret_friend_id = '" + secretFriendId + '\'' + 
			",email_address = '" + emailAddress + '\'' + 
			",friend_circle_creator_last_name = '" + friendCircleCreatorLastName + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",friend_circle_id = '" + friendCircleId + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",friend_circle_creator_first_name = '" + friendCircleCreatorFirstName + '\'' + 
			"}";
		}
}