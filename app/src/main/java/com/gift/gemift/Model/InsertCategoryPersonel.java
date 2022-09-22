package com.gift.gemift.Model;

import java.io.Serializable;

public class InsertCategoryPersonel implements Serializable {

    private List_category_id[] list_category_id;

    private String user_id;


    private String request_type;

    public InsertCategoryPersonel() {

    }

    public InsertCategoryPersonel(List_category_id[] list_category_id, String referred_user_id, String request_type) {
        this.list_category_id = list_category_id;
        this.user_id = referred_user_id;
        this.request_type = request_type;
    }

    public List_category_id[] getList_category_id() {
        return list_category_id;
    }

    public void setList_category_id(List_category_id[] list_category_id) {
        this.list_category_id = list_category_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }
}
