package com.gift.gemift.Model;

import com.gift.gemift.Storage.DB.Entity.UserDetailEntity;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private String email;

    private String password;

    private String token;
    private String country_code;

    private UserDetailEntity[] data;

    private String phone_number;

    public LoginResponse(){

    }

    public LoginResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDetailEntity[] getData() {
        return data;
    }

    public void setData(UserDetailEntity[] data) {
        this.data = data;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
