package com.gift.gemift.Model;

public class OccasionsModel {
    public String friend_circle_id;
    public String secret_friend;
    public String secret_first_name;
    public String secret_last_name;
    public int flag;
    public int days_since;

    public int getDays_since() {
        return days_since;
    }

    public void setDays_since(int days_since) {
        this.days_since = days_since;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getSecret_friend() {
        return secret_friend;
    }

    public void setSecret_friend(String secret_friend) {
        this.secret_friend = secret_friend;
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
