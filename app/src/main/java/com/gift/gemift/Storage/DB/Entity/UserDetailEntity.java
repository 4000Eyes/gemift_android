package com.gift.gemift.Storage.DB.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "db_user_detail")
public class UserDetailEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int AutoId;

    private String email_address;

    private String phone_number;

    private String first_name;

    private String last_name;

    private String location;

    private String gender;

    private String logged_user_id;

    private String image_url;

    public int getAutoId() {
        return AutoId;
    }

    public void setAutoId(int autoId) {
        AutoId = autoId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLogged_user_id() {
        return logged_user_id;
    }

    public void setLogged_user_id(String logged_user_id) {
        this.logged_user_id = logged_user_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
