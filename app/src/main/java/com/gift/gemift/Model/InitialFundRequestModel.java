package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class InitialFundRequestModel{

	@SerializedName("notes")
	private String notes;

	@SerializedName("misc_cost")
	private Double miscCost;

	@SerializedName("request_type")
	private String requestType;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("product_price")
	private Double productPrice;

	@SerializedName("expiration_date")
	private String expirationDate;

	@SerializedName("initiated_date")
	private String initiated_date;

	@SerializedName("time_zone")
	private String timeZone;

	@SerializedName("occasion_id")
	private String occasionId;

	@SerializedName("email_address")
	private String emailAddress;

	@SerializedName("occasion_date")
	private String occasionDate;

	@SerializedName("occasion_name")
	private String occasionName;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("friend_circle_id")
	private String friendCircleId;

	@SerializedName("first_name")
	private String firstName;


	@SerializedName("country_code")
	private String country_code;


	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
	}

	public void setMiscCost(Double miscCost){
		this.miscCost = miscCost;
	}

	public Double getMiscCost(){
		return miscCost;
	}

	public void setRequestType(String requestType){
		this.requestType = requestType;
	}

	public String getRequestType(){
		return requestType;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setProductPrice(Double productPrice){
		this.productPrice = productPrice;
	}

	public Double getProductPrice(){
		return productPrice;
	}

	public void setExpirationDate(String expirationDate){
		this.expirationDate = expirationDate;
	}

	public String getExpirationDate(){
		return expirationDate;
	}

	public void setTimeZone(String timeZone){
		this.timeZone = timeZone;
	}

	public String getTimeZone(){
		return timeZone;
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

	public void setOccasionDate(String occasionDate){
		this.occasionDate = occasionDate;
	}

	public String getOccasionDate(){
		return occasionDate;
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

	public String getOccasionName() {
		return occasionName;
	}

	public void setOccasionName(String occasionName) {
		this.occasionName = occasionName;
	}

	public String getInitiated_date() {
		return initiated_date;
	}

	public void setInitiated_date(String initiated_date) {
		this.initiated_date = initiated_date;
	}
}