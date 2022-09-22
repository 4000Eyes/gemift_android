package com.gift.gemift.Storage.DB.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "db_friend_list")
public class FriendListEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int Autoid;

    private String email_address;

    private String user_id;

    private String first_name;

    private String last_name;

    private String gender;

    private String relationship;

    private String friend_circle_id;

    private String friend_circle_name;

    private String secret_friend_id;

    private String secret_first_name;

    private String secret_last_name;

    private String image_url;

    private String phone_number;

    private int approval_status;

    public FriendListEntity(String email_address, String user_id, String first_name, String last_name, String gender, String relationship, String friend_circle_id, String friend_circle_name, String secret_friend_id, String secret_first_name, String secret_last_name, String image_url, int approval_status) {
        this.email_address = email_address;
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.relationship = relationship;
        this.friend_circle_id = friend_circle_id;
        this.friend_circle_name = friend_circle_name;
        this.secret_friend_id = secret_friend_id;
        this.secret_first_name = secret_first_name;
        this.secret_last_name = secret_last_name;
        this.image_url = image_url;
        this.approval_status = approval_status;
    }


    public int getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(int approval_status) {
        this.approval_status = approval_status;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getFriend_circle_name() {
        return friend_circle_name;
    }

    public void setFriend_circle_name(String friend_circle_name) {
        this.friend_circle_name = friend_circle_name;
    }

    public int getAutoid() {
        return Autoid;
    }

    public void setAutoid(int autoid) {
        Autoid = autoid;
    }

    public String getSecret_friend_id() {
        return secret_friend_id;
    }

    public void setSecret_friend_id(String secret_friend_id) {
        this.secret_friend_id = secret_friend_id;
    }

    public String getSecret_first_name() {
        return secret_first_name;
    }

    public void setSecret_first_name(String secret_first_name) {
        this.secret_first_name = secret_first_name;
    }

    public String getSecret_last_name() {
        return secret_last_name;
    }

    public void setSecret_last_name(String secret_last_name) {
        this.secret_last_name = secret_last_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
