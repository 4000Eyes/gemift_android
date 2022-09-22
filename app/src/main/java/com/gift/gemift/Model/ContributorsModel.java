package com.gift.gemift.Model;

import java.io.Serializable;

public class ContributorsModel implements Serializable {

    private String user_id;
    private String full_name;
    private String email_address;
    private String phone_number;
    private String image_url;
    private String first_name;
    private String last_name;
    private String gender;
    private String location;
    private String age;
    private String contact_identity;
    private boolean isSelected = false;;
    private String country_code;

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getContact_identity() {
        return contact_identity;
    }

    public void setContact_identity(String contact_identity) {
        this.contact_identity = contact_identity;
    }

    public ContributorsModel(String user_id,String full_name, String email_address, String phone_number, String image_url, String first_name, String last_name, String gender, String location,String contact_identity) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.image_url = image_url;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.location = location;
        this.contact_identity = contact_identity;
    }

    public  ContributorsModel(){

    }
}
