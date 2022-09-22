package com.gift.gemift.Model;

import java.io.Serializable;

public class OccasionInsertResponse implements Serializable {

    private String creator_user_id;

    private String value_timezone;

    private String occasion_date;

    private String occasion_name;

    private String friend_circle_id;

    private int request_id;

    private String frequency;

    private String occasion_id;

    public OccasionInsertResponse(String creator_user_id, String value_timezone, String occasion_date, String occasion_name, String friend_circle_id, int request_id, String frequency) {
        this.creator_user_id = creator_user_id;
        this.value_timezone = value_timezone;
        this.occasion_date = occasion_date;
        this.occasion_name = occasion_name;
        this.friend_circle_id = friend_circle_id;
        this.request_id = request_id;
        this.frequency = frequency;
    }

    public OccasionInsertResponse(String creator_user_id, String value_timezone, String occasion_date, String friend_circle_id, int request_id, String occasion_id) {
        this.creator_user_id = creator_user_id;
        this.value_timezone = value_timezone;
        this.occasion_date = occasion_date;
        this.friend_circle_id = friend_circle_id;
        this.request_id = request_id;
        this.occasion_id = occasion_id;
    }

    public String getOccasion_id() {
        return occasion_id;
    }

    public void setOccasion_id(String occasion_id) {
        this.occasion_id = occasion_id;
    }

    public String getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(String creator_user_id) {
        this.creator_user_id = creator_user_id;
    }

    public String getValue_timezone() {
        return value_timezone;
    }

    public void setValue_timezone(String value_timezone) {
        this.value_timezone = value_timezone;
    }

    public String getOccasion_date() {
        return occasion_date;
    }

    public void setOccasion_date(String occasion_date) {
        this.occasion_date = occasion_date;
    }

    public String getOccasion_name() {
        return occasion_name;
    }

    public void setOccasion_name(String occasion_name) {
        this.occasion_name = occasion_name;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

}
