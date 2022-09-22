package com.gift.gemift.Model;

import java.io.Serializable;

public class OccasionDetailResponse implements Serializable {

    private String occasion_id;

    private String occasion_name;

    private String friend_circle_id;

    private String occasion_frequency;

    public String getOccasion_id() {
        return occasion_id;
    }

    public void setOccasion_id(String occasion_id) {
        this.occasion_id = occasion_id;
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

    public String getOccasion_frequency() {
        return occasion_frequency;
    }

    public void setOccasion_frequency(String occasion_frequency) {
        this.occasion_frequency = occasion_frequency;
    }
}
