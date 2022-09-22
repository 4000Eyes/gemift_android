package com.gift.gemift.Model;

import java.io.Serializable;

public class SecretFriendAgeDetail implements Serializable {


    public String secret_last_name;

    public String secret_first_name;

    public String gender;

    public String age;

    public String friend_circle_id;

    public String getSecret_last_name() {
        return secret_last_name;
    }

    public void setSecret_last_name(String secret_last_name) {
        this.secret_last_name = secret_last_name;
    }

    public String getSecret_first_name() {
        return secret_first_name;
    }

    public void setSecret_first_name(String secret_first_name) {
        this.secret_first_name = secret_first_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }
}
