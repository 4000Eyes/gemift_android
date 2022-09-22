package com.gift.gemift.Model;

import java.io.Serializable;

public class InterestList implements Serializable {

    private String web_category_id;

    private String web_category_name;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getWeb_category_id() {
        return web_category_id;
    }

    public void setWeb_category_id(String web_category_id) {
        this.web_category_id = web_category_id;
    }

    public String getWeb_category_name() {
        return web_category_name;
    }

    public void setWeb_category_name(String web_category_name) {
        this.web_category_name = web_category_name;
    }
}
