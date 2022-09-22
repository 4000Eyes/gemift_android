package com.gift.gemift.Model;

import java.io.Serializable;

public class AddFriendModel implements Serializable {

    private String email_address;

    private String gender;

    private String referrer_user_id;

    private String last_name;

    private String phone_number;

    private int request_id;

    private String first_name;

    private String status;

    private String group_name;

    private String friend_circle_id;

    private String referred_user_id;

    private String image_url;
    private String location;
    private String age;

    private String country_code;
private String signal;


    public AddFriendModel(){

    }

    public AddFriendModel(String email_address, String gender, String referrer_user_id, String last_name, String phone_number, int request_id, String first_name,String group_name,String image_url,String location) {
        this.email_address = email_address;
        this.gender = gender;
        this.referrer_user_id = referrer_user_id;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.request_id = request_id;
        this.first_name = first_name;
        this.group_name = group_name;
        this.image_url = image_url;
        this.location = location;

    }

    public AddFriendModel(String email_address, String gender, String referrer_user_id, String last_name, String phone_number, int request_id, String first_name) {
        this.email_address = email_address;
        this.gender = gender;
        this.referrer_user_id = referrer_user_id;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.request_id = request_id;
        this.first_name = first_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getEmail_address ()
    {
        return email_address;
    }

    public void setEmail_address (String email_address)
    {
        this.email_address = email_address;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getReferrer_user_id ()
    {
        return referrer_user_id;
    }

    public void setReferrer_user_id (String referrer_user_id)
    {
        this.referrer_user_id = referrer_user_id;
    }

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
    }

    public String getPhone_number ()
    {
        return phone_number;
    }

    public void setPhone_number (String phone_number)
    {
        this.phone_number = phone_number;
    }

    public int getRequest_id ()
    {
        return request_id;
    }

    public void setRequest_id (int request_id)
    {
        this.request_id = request_id;
    }

    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getReferred_user_id() {
        return referred_user_id;
    }

    public void setReferred_user_id(String referred_user_id) {
        this.referred_user_id = referred_user_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
