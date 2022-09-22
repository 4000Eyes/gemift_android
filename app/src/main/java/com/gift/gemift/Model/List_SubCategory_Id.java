package com.gift.gemift.Model;

import java.io.Serializable;

public class List_SubCategory_Id implements Serializable {

    private String web_subcategory_id;

    private int vote;

    public List_SubCategory_Id(String web_subcategory_id, int vote) {
        this.web_subcategory_id = web_subcategory_id;
        this.vote = vote;
    }

    public String getWeb_subcategory_id() {
        return web_subcategory_id;
    }

    public void setWeb_subcategory_id(String web_subcategory_id) {
        this.web_subcategory_id = web_subcategory_id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
