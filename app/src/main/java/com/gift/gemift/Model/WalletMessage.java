package com.gift.gemift.Model;

import java.io.Serializable;

public class WalletMessage implements Serializable {
    private String transaction_id;

    private String user_id;

    private String message_datetime;

    private String message_type;

    private String friend_circle_id;

    private String message;

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

    public String getMessage_datetime() {
        return message_datetime;
    }

    public void setMessage_datetime(String message_datetime) {
        this.message_datetime = message_datetime;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
