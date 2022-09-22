package com.gift.gemift.Model;

import java.io.Serializable;

public class FriendCircleListResponse implements Serializable {

    private String friend_circle_name;

    private String secret_friend_id;

    private String total_contributors;

    private String secret_first_name;

    private String image_url;

    private String contrib_status;

    private String friend_circle_id;

    private String secret_last_name;

    private String age;

    private categoryList[] category;

    private categoryList[] subcategory;

    public String getFriend_circle_name() {
        return friend_circle_name;
    }

    public void setFriend_circle_name(String friend_circle_name) {
        this.friend_circle_name = friend_circle_name;
    }

    public String getSecret_friend_id() {
        return secret_friend_id;
    }

    public void setSecret_friend_id(String secret_friend_id) {
        this.secret_friend_id = secret_friend_id;
    }

    public String getTotal_contributors() {
        return total_contributors;
    }

    public void setTotal_contributors(String total_contributors) {
        this.total_contributors = total_contributors;
    }

    public String getSecret_first_name() {
        return secret_first_name;
    }

    public void setSecret_first_name(String secret_first_name) {
        this.secret_first_name = secret_first_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getContrib_status() {
        return contrib_status;
    }

    public void setContrib_status(String contrib_status) {
        this.contrib_status = contrib_status;
    }

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getSecret_last_name() {
        return secret_last_name;
    }

    public void setSecret_last_name(String secret_last_name) {
        this.secret_last_name = secret_last_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public categoryList[] getCategory() {
        return category;
    }

    public void setCategory(categoryList[] category) {
        this.category = category;
    }

    public categoryList[] getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(categoryList[] subcategory) {
        this.subcategory = subcategory;
    }

    public class categoryList implements Serializable {

        private String friend_circle_id;

        private String count;

        public String getFriend_circle_id() {
            return friend_circle_id;
        }

        public void setFriend_circle_id(String friend_circle_id) {
            this.friend_circle_id = friend_circle_id;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

}
