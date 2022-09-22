package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Subcategory implements Serializable {


    @SerializedName("interest_id")
    private String web_subcategory_id;

    @SerializedName("interest_name")
    private String web_subcategory_name;
    @SerializedName("interest_desc")
    private String web_subcategory_desc;
    @SerializedName("image_url")
    private String image_path;

    public String getWeb_subcategory_id() {
        return web_subcategory_id;
    }

    public void setWeb_subcategory_id(String web_subcategory_id) {
        this.web_subcategory_id = web_subcategory_id;
    }


    public String getWeb_subcategory_desc() {
        return web_subcategory_desc;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setWeb_subcategory_desc(String web_subcategory_desc) {
        this.web_subcategory_desc = web_subcategory_desc;
    }

    public String getWeb_subcategory_name() {
        return web_subcategory_name;
    }

    public void setWeb_subcategory_name(String web_subcategory_name) {
        this.web_subcategory_name = web_subcategory_name;
    }
}
