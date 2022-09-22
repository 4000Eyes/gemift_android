package com.gift.gemift.Model;

import java.io.Serializable;

public class InsertVoteProduct implements Serializable {

    public int request_id;
        public String product_id;
        public String product_title;
        public String price;
        public String friend_circle_id;
        public String user_id;
        public int vote;
        public String comment;
        public String occasion_name;
        public String occasion_year;
        public String occasion_id;

    public String getOccasionId() {
        return occasion_id;
    }

    public void setOccasionId(String occasionId) {
        this.occasion_id = occasionId;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }


    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOccasion_name() {
        return occasion_name;
    }

    public void setOccasion_name(String occasion_name) {
        this.occasion_name = occasion_name;
    }

    public String getOccasion_year() {
        return occasion_year;
    }

    public void setOccasion_year(String occasion_year) {
        this.occasion_year = occasion_year;
    }

    public InsertVoteProduct(int request_id, String product_id, String product_title, String price, String friend_circle_id, String user_id, int vote, String comment, String occasion_name, String occasion_year) {
        this.request_id = request_id;
        this.product_id = product_id;
        this.product_title = product_title;
        this.price = price;
        this.friend_circle_id = friend_circle_id;
        this.user_id = user_id;
        this.vote = vote;
        this.comment = comment;
        this.occasion_name = occasion_name;
        this.occasion_year = occasion_year;
    }
}
