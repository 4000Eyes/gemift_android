package com.gift.gemift.Model;

import retrofit2.http.Field;

public class Optin {

    String request_type;
    String transaction_id;
    String user_id;
    String opt_in_flag;
    String paid_amount;


    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOpt_in_flag() {
        return opt_in_flag;
    }

    public void setOpt_in_flag(String opt_in_flag) {
        this.opt_in_flag = opt_in_flag;
    }
}
