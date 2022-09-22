package com.gift.gemift.Model;

import java.io.Serializable;

public class InitiateFundCreator implements Serializable {

    private String notes;

    private double misc_cost;

    private String last_name;

    private double product_price;

    private String expiration_date;

    private String time_zone;

    private String occasion_id;

    private String email_address;

    private String user_id;

    private String occasion_date;

    private String product_id;

    private String phone_number;

    private String friend_circle_id;

    private String first_name;

    public InitiateFundCreator(){

    }
    public InitiateFundCreator(String notes, double misc_cost, String last_name, double product_price, String expiration_date, String time_zone, String occasion_id, String email_address, String user_id, String occasion_date, String product_id, String phone_number, String friend_circle_id, String first_name) {
        this.notes = notes;
        this.misc_cost = misc_cost;
        this.last_name = last_name;
        this.product_price = product_price;
        this.expiration_date = expiration_date;
        this.time_zone = time_zone;
        this.occasion_id = occasion_id;
        this.email_address = email_address;
        this.user_id = user_id;
        this.occasion_date = occasion_date;
        this.product_id = product_id;
        this.phone_number = phone_number;
        this.friend_circle_id = friend_circle_id;
        this.first_name = first_name;
    }

    public String getNotes ()
    {
        return notes;
    }

    public void setNotes (String notes)
    {
        this.notes = notes;
    }

    public double getMisc_cost ()
    {
        return misc_cost;
    }

    public void setMisc_cost (double misc_cost)
    {
        this.misc_cost = misc_cost;
    }

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
    }

    public double getProduct_price ()
    {
        return product_price;
    }

    public void setProduct_price (double product_price)
    {
        this.product_price = product_price;
    }

    public String getExpiration_date ()
    {
        return expiration_date;
    }

    public void setExpiration_date (String expiration_date)
    {
        this.expiration_date = expiration_date;
    }

    public String getTime_zone ()
    {
        return time_zone;
    }

    public void setTime_zone (String time_zone)
    {
        this.time_zone = time_zone;
    }

    public String getOccasion_id ()
    {
        return occasion_id;
    }

    public void setOccasion_id (String occasion_id)
    {
        this.occasion_id = occasion_id;
    }

    public String getEmail_address ()
    {
        return email_address;
    }

    public void setEmail_address (String email_address)
    {
        this.email_address = email_address;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getOccasion_date ()
    {
        return occasion_date;
    }

    public void setOccasion_date (String occasion_date)
    {
        this.occasion_date = occasion_date;
    }

    public String getProduct_id ()
    {
        return product_id;
    }

    public void setProduct_id (String product_id)
    {
        this.product_id = product_id;
    }

    public String getPhone_number ()
    {
        return phone_number;
    }

    public void setPhone_number (String phone_number)
    {
        this.phone_number = phone_number;
    }

    public String getFriend_circle_id ()
    {
        return friend_circle_id;
    }

    public void setFriend_circle_id (String friend_circle_id)
    {
        this.friend_circle_id = friend_circle_id;
    }

    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }

}

