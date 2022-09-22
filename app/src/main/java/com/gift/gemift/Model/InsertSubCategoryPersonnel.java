package com.gift.gemift.Model;

import java.io.Serializable;
import java.util.List;

public class InsertSubCategoryPersonnel implements Serializable {
    private List<List_SubCategory_Id> list_subcategory_id;

    private String user_id;


    private String request_type;

    public InsertSubCategoryPersonnel(List<List_SubCategory_Id> list_subcategory_id, String user_id, String request_type) {
        this.list_subcategory_id = list_subcategory_id;
        this.user_id = user_id;
        this.request_type = request_type;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public List<List_SubCategory_Id> getList_subcategory_id() {
        return list_subcategory_id;
    }

    public void setList_subcategory_id(List<List_SubCategory_Id> list_subcategory_id) {
        this.list_subcategory_id = list_subcategory_id;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }
}
