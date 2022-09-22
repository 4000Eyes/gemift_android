package com.gift.gemift.Model;

import java.io.Serializable;

public class UpdateFriendAgeResponse implements Serializable {


    private String gender;

    private int age;

    private String friend_circle_id;

    private String user_id;

    private int request_id;

    public UpdateFriendAgeResponse(String gender, int age, String friend_circle_id, String user_id, int request_id) {
        this.gender = gender;
        this.age = age;
        this.friend_circle_id = friend_circle_id;
        this.user_id = user_id;
        this.request_id = request_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
