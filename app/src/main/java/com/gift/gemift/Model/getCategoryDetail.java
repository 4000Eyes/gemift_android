package com.gift.gemift.Model;

import java.io.Serializable;

public class getCategoryDetail implements Serializable {

    private String category_name;

    private String category_id;

    private String votes;

    private String users;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getUsers() {
        return users;
    }
}
