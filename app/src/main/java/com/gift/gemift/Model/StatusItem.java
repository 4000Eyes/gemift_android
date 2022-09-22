package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class StatusItem{

	@SerializedName("secret_friend_id")
	private String secretFriendId;

	@SerializedName("last_created_date")
	String last_created_date;

	@SerializedName("description")
	String description;

	@SerializedName("friend_circle_name")
	String friend_circle_name;



	@SerializedName("user_id")
	private String userId;

	@SerializedName("secret_first_name")
	private String secretFirstName;

	@SerializedName("type_id")
	private String typeId;

	@SerializedName("days_since")
	private int daysSince;

	@SerializedName("message_id")
	private String messageId;

	@SerializedName("friend_circle_id")
	private String friendCircleId;

	@SerializedName("secret_last_name")
	private String secretLastName;

	@SerializedName("flag")
	private int flag;

	@SerializedName("message_type")
	private String message_type;

	@SerializedName("transaction_id")
	private String transaction_id;

	@SerializedName("type_id ")
	String type_id;


	public String getTransaction_id() {
		return transaction_id;
	}


	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getLast_created_date() {
		return last_created_date;
	}

	public void setLast_created_date(String last_created_date) {
		this.last_created_date = last_created_date;
	}

	public void setSecretFriendId(String secretFriendId){
		this.secretFriendId = secretFriendId;
	}

	public String getSecretFriendId(){
		return secretFriendId;
	}



	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setSecretFirstName(String secretFirstName){
		this.secretFirstName = secretFirstName;
	}

	public String getSecretFirstName(){
		return secretFirstName;
	}

	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return typeId;
	}

	public void setDaysSince(int daysSince){
		this.daysSince = daysSince;
	}

	public int getDaysSince(){
		return daysSince;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setMessageId(String messageId){
		this.messageId = messageId;
	}

	public String getMessageId(){
		return messageId;
	}

	public void setFriendCircleId(String friendCircleId){
		this.friendCircleId = friendCircleId;
	}

	public String getFriendCircleId(){
		return friendCircleId;
	}

	public void setSecretLastName(String secretLastName){
		this.secretLastName = secretLastName;
	}

	public String getSecretLastName(){
		return secretLastName;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag(){
		return flag;
	}

	public String getFriend_circle_name() {
		return friend_circle_name;
	}

	public void setFriend_circle_name(String friend_circle_name) {
		this.friend_circle_name = friend_circle_name;
	}
}