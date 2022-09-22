package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOccasionDetailResponse implements Serializable {

    private String occasion_id;

    private String occasion_date;

    private int days_left;

    private String occasion_frequency;

    private String occasion_name;

    private String next_occasion_date;

    private String message;


    private int product_count;


    private String occasion_active_status;


    public String getOccasionActiveStatus() {
        return occasion_active_status;
    }

    public void setOccasionActiveStatus(String occasionActiveStatus) {
        this.occasion_active_status = occasionActiveStatus;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
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

    public int getDays_left() {
        return days_left;
    }

    public void setDays_left(int days_left) {
        this.days_left = days_left;
    }

    public String getOccasion_frequency() {
        return occasion_frequency;
    }

    public void setOccasion_frequency(String occasion_frequency) {
        this.occasion_frequency = occasion_frequency;
    }

    public String getOccasion_name() {
        return occasion_name;
    }

    public void setOccasion_name(String occasion_name) {
        this.occasion_name = occasion_name;
    }

    public String getNext_occasion_date() {
        return next_occasion_date;
    }

    public void setNext_occasion_date(String next_occasion_date) {
        this.next_occasion_date = next_occasion_date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
