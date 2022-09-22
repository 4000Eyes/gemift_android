package com.gift.gemift.Model;

import java.io.Serializable;

public class DataModel implements Serializable {

    public String friend_circle_id;

    public String secret_friend;

    public String secret_first_name;

    public String secret_last_name;

    public int flag;

    public int days_since;

    public String linked_user_id;

    public String friend_circle_name;

    public String secret_friend_id;

    public String user_id;

    public String first_name;

    public String last_name;
    public String referred_user_id;
    public  String referrer_user_id;


    public String getReferred_user_id() {
        return referred_user_id;
    }

    public void setReferred_user_id(String referred_user_id) {
        this.referred_user_id = referred_user_id;
    }

    public String getReferrer_user_id() {
        return referrer_user_id;
    }

    public void setReferrer_user_id(String referrer_user_id) {
        this.referrer_user_id = referrer_user_id;
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

    public int getDays_since() {
        return days_since;
    }

    public void setDays_since(int days_since) {
        this.days_since = days_since;
    }

    public String getLinked_user_id() {
        return linked_user_id;
    }

    public void setLinked_user_id(String linked_user_id) {
        this.linked_user_id = linked_user_id;
    }

    public String getFriend_circle_name() {
        return friend_circle_name;
    }

    public void setFriend_circle_name(String friend_circle_name) {
        this.friend_circle_name = friend_circle_name;
    }

    public String getSecret_friend_id() {
        return secret_friend_id;
    }

    public void setSecret_friend_id(String secret_friend_id) {
        this.secret_friend_id = secret_friend_id;
    }
}
