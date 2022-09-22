package com.gift.gemift.Model.Wallet;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Header implements Serializable {

	@SerializedName("transaction_status")
	private int transactionStatus;

	@SerializedName("misc_cost")
	private double miscCost;

	@SerializedName("secret_first_name")
	private String secretFirstName;

	@SerializedName("product_price")
	private double productPrice;

	@SerializedName("expiration_date")
	private String expirationDate;

	@SerializedName("occasion_date")
	private Object occasionDate;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("friend_circle_creator_last_name")
	private String friendCircleCreatorLastName;

	@SerializedName("friend_circle_id")
	private String friendCircleId;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("friend_circle_creator_first_name")
	private String friendCircleCreatorFirstName;

	@SerializedName("paying_numbers")
	private int payingNumbers;

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("friend_circle_name")
	private String friendCircleName;

	@SerializedName("image_url")
	private Object imageUrl;

	@SerializedName("friend_circle_creator_id")
	private String friendCircleCreatorId;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("secret_last_name")
	private String secretLastName;

	@SerializedName("occasion_id")
	private String occasionId;

	@SerializedName("email_address")
	private String emailAddress;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("paid_amount")
	private int paidAmount;

	@SerializedName("initiated_date")
	private String initiatedDate;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("occasion_name")
	private String occasionName;

	public void setTransactionStatus(int transactionStatus){
		this.transactionStatus = transactionStatus;
	}

	public int getTransactionStatus(){
		return transactionStatus;
	}

	public void setMiscCost(double miscCost){
		this.miscCost = miscCost;
	}

	public double getMiscCost(){
		return miscCost;
	}

	public void setSecretFirstName(String secretFirstName){
		this.secretFirstName = secretFirstName;
	}

	public String getSecretFirstName(){
		return secretFirstName;
	}

	public void setProductPrice(double productPrice){
		this.productPrice = productPrice;
	}

	public double getProductPrice(){
		return productPrice;
	}

	public void setExpirationDate(String expirationDate){
		this.expirationDate = expirationDate;
	}

	public String getExpirationDate(){
		return expirationDate;
	}

	public void setOccasionDate(Object occasionDate){
		this.occasionDate = occasionDate;
	}

	public Object getOccasionDate(){
		return occasionDate;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setFriendCircleCreatorLastName(String friendCircleCreatorLastName){
		this.friendCircleCreatorLastName = friendCircleCreatorLastName;
	}

	public String getFriendCircleCreatorLastName(){
		return friendCircleCreatorLastName;
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

	public void setPayingNumbers(int payingNumbers){
		this.payingNumbers = payingNumbers;
	}

	public int getPayingNumbers(){
		return payingNumbers;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setFriendCircleName(String friendCircleName){
		this.friendCircleName = friendCircleName;
	}

	public String getFriendCircleName(){
		return friendCircleName;
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

	public void setOccasionId(String occasionId){
		this.occasionId = occasionId;
	}

	public String getOccasionId(){
		return occasionId;
	}

	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setPaidAmount(int paidAmount){
		this.paidAmount = paidAmount;
	}

	public int getPaidAmount(){
		return paidAmount;
	}

	public void setInitiatedDate(String initiatedDate){
		this.initiatedDate = initiatedDate;
	}

	public String getInitiatedDate(){
		return initiatedDate;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setOccasionName(String occasionName){
		this.occasionName = occasionName;
	}

	public String getOccasionName(){
		return occasionName;
	}
}