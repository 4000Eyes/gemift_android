package com.gift.gemift.Model;

import java.io.Serializable;

public class InsertVoteOccasion implements Serializable {

    private String creator_user_id;

    private String occasion_id;

    private String value_timezone;

    private int flag;

    private String friend_circle_id;

    private int request_id;

    private String value;

    public InsertVoteOccasion(String creator_user_id, String occasion_id, String value_timezone, int flag, String friend_circle_id, int request_id, String value) {
        this.creator_user_id = creator_user_id;
        this.occasion_id = occasion_id;
        this.value_timezone = value_timezone;
        this.flag = flag;
        this.friend_circle_id = friend_circle_id;
        this.request_id = request_id;
        this.value = value;
    }

    public String getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(String creator_user_id) {
        this.creator_user_id = creator_user_id;
    }

    public String getOccasion_id() {
        return occasion_id;
    }

    public void setOccasion_id(String occasion_id) {
        this.occasion_id = occasion_id;
    }

    public String getValue_timezone() {
        return value_timezone;
    }

    public void setValue_timezone(String value_timezone) {
        this.value_timezone = value_timezone;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
