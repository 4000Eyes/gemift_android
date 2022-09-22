package com.gift.gemift.Model;

public class UnapprovedOccasion {
    public String friend_circle_id;
    public String occasion_name;
    public String occasion_date;
    public String occasion_id;
    public String secret_friend_id;
    public String secret_first_name;
    public String secret_last_name;
    public String creator_user_id;
    public String flag;
    public int request_id;
    public String api_call_time;
    public  String time_zone;


    public String getApi_call_time() {
        return api_call_time;
    }

    public void setApi_call_time(String api_call_time) {
        this.api_call_time = api_call_time;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(String creator_user_id) {
        this.creator_user_id = creator_user_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getOccasion_name() {
        return occasion_name;
    }

    public void setOccasion_name(String occasion_name) {
        this.occasion_name = occasion_name;
    }

    public String getOccasion_date() {
        return occasion_date;
    }

    public void setOccasion_date(String occasion_date) {
        this.occasion_date = occasion_date;
    }

    public String getOccasion_id() {
        return occasion_id;
    }

    public void setOccasion_id(String occasion_id) {
        this.occasion_id = occasion_id;
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
}
