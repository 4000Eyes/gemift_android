package com.gift.gemift.Model;

import java.io.Serializable;

public class InitiateFundFriends implements Serializable {

    private String opt_in_flag;

    private String email_address;

    private String user_id;

    private String opt_in_date;

    private String last_name;

    private String phone_number;

    private String first_name;

    public InitiateFundFriends(String opt_in_flag, String email_address, String user_id, String opt_in_date, String last_name, String phone_number, String first_name) {
        this.opt_in_flag = opt_in_flag;
        this.email_address = email_address;
        this.user_id = user_id;
        this.opt_in_date = opt_in_date;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.first_name = first_name;
    }

    public String getOpt_in_flag ()
    {
        return opt_in_flag;
    }

    public void setOpt_in_flag (String opt_in_flag)
    {
        this.opt_in_flag = opt_in_flag;
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

    public String getOpt_in_date ()
    {
        return opt_in_date;
    }

    public void setOpt_in_date (String opt_in_date)
    {
        this.opt_in_date = opt_in_date;
    }

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
    }

    public String getPhone_number ()
    {
        return phone_number;
    }

    public void setPhone_number (String phone_number)
    {
        this.phone_number = phone_number;
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
