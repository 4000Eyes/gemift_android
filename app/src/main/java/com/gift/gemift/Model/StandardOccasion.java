package com.gift.gemift.Model;

import java.io.Serializable;

public class StandardOccasion implements Serializable {

    private int request_id;
    private String friend_circle_id;
    private String creator_user_id;
    private String occasion_id;
    private String occasion_date;
    private String occasion_timezone;

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
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

    public String getOccasion_date() {
        return occasion_date;
    }

    public void setOccasion_date(String occasion_date) {
        this.occasion_date = occasion_date;
    }

    public String getOccasion_timezone() {
        return occasion_timezone;
    }

    public void setOccasion_timezone(String occasion_timezone) {
        this.occasion_timezone = occasion_timezone;
    }

    public StandardOccasion(int request_id, String friend_circle_id, String creator_user_id, String occasion_id, String occasion_date, String occasion_timezone) {
        this.request_id = request_id;
        this.friend_circle_id = friend_circle_id;
        this.creator_user_id = creator_user_id;
        this.occasion_id = occasion_id;
        this.occasion_date = occasion_date;
        this.occasion_timezone = occasion_timezone;
    }
}
