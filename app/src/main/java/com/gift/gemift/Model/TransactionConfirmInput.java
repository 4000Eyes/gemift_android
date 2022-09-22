package com.gift.gemift.Model;

public class TransactionConfirmInput {

   String request_type;
    String transaction_id;
    String time_zone;
    String api_call_time;


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

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getApi_call_time() {
        return api_call_time;
    }

    public void setApi_call_time(String api_call_time) {
        this.api_call_time = api_call_time;
    }
}
