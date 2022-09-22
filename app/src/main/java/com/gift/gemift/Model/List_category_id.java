package com.gift.gemift.Model;

import java.io.Serializable;

public class List_category_id implements Serializable {

    private String web_category_id;

    private int vote;


    public List_category_id(String web_category_id, int vote) {
        this.web_category_id = web_category_id;
        this.vote = vote;
    }

    public String getWeb_category_id() {
        return web_category_id;
    }

    public void setWeb_category_id(String web_category_id) {
        this.web_category_id = web_category_id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
