package com.gift.gemift.Model;

import java.io.Serializable;

public class InsertCategory implements Serializable {

    private List_category_id[] list_category_id;

    private String referred_user_id;

    private String friend_circle_id;

    private int request_id;

    public InsertCategory() {

    }

    public InsertCategory(List_category_id[] list_category_id, String referred_user_id, String friend_circle_id, int request_id) {
        this.list_category_id = list_category_id;
        this.referred_user_id = referred_user_id;
        this.friend_circle_id = friend_circle_id;
        this.request_id = request_id;
    }

    public List_category_id[] getList_category_id() {
        return list_category_id;
    }

    public void setList_category_id(List_category_id[] list_category_id) {
        this.list_category_id = list_category_id;
    }

    public String getReferred_user_id() {
        return referred_user_id;
    }

    public void setReferred_user_id(String referred_user_id) {
        this.referred_user_id = referred_user_id;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
