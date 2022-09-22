package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignUpResponse implements Serializable {
    private String email;

    private Integer user_type;

    private String password;

    private String phone_number;

    private String gender;

    private String first_name;

    private String last_name;

    private String external_referrer_id;

    private String external_referrer_param;

    private String location;

    private String user_id;

    private  String image_url;
    private String country_code;

    @SerializedName("api_call_time")
    private String apiCallTime;

    @SerializedName("time_zone")
    private String timeZone;



    public SignUpResponse(String email, Integer user_type, String password, String phone_number, String gender, String first_name, String last_name, String external_referrer_id, String external_referrer_param,String location, String image_url, String country_code) {
        this.email = email;
        this.user_type = user_type;
        this.password = password;
        this.phone_number = phone_number;
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
        this.external_referrer_id = external_referrer_id;
        this.external_referrer_param = external_referrer_param;
        this.location = location;
        this.image_url = image_url;
        this.country_code = country_code;
    }


    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getApiCallTime() {
        return apiCallTime;
    }

    public void setApiCallTime(String apiCallTime) {
        this.apiCallTime = apiCallTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getExternal_referrer_id() {
        return external_referrer_id;
    }

    public void setExternal_referrer_id(String external_referrer_id) {
        this.external_referrer_id = external_referrer_id;
    }

    public String getExternal_referrer_param() {
        return external_referrer_param;
    }

    public void setExternal_referrer_param(String external_referrer_param) {
        this.external_referrer_param = external_referrer_param;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
