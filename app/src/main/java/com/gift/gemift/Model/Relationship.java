package com.gift.gemift.Model;

import java.io.Serializable;

public class Relationship implements Serializable {

    public String friend_circle_id;
    public String secret_friend_id;
    public String secret_first_name;
    public String secret_last_name;
    public String user_id;
    public int days_since;
    public int flag;

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getDays_since() {
        return days_since;
    }

    public void setDays_since(int days_since) {
        this.days_since = days_since;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
