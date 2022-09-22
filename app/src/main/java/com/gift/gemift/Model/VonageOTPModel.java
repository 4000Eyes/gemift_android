package com.gift.gemift.Model;

import java.io.Serializable;

public class VonageOTPModel implements Serializable {

    private String vonage_request_id;

    private int vonage_response_code;

    private int request_id;

    private String phone_number;

    private String status;

    private String event_id;
    private String country_code;

    public VonageOTPModel(){

    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getVonage_request_id() {
        return vonage_request_id;
    }

    public void setVonage_request_id(String vonage_request_id) {
        this.vonage_request_id = vonage_request_id;
    }

    public int getVonage_response_code() {
        return vonage_response_code;
    }

    public void setVonage_response_code(int vonage_response_code) {
        this.vonage_response_code = vonage_response_code;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
}
