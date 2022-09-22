package com.gift.gemift.Model;

import java.io.Serializable;

public class ContributorInvite implements Serializable {

    public String friend_circle_id;
    public String linked_user_id;
    public String friend_circle_name;
    public String secret_first_name;
    public String secret_last_name;
    public String secret_friend_id;

    public String getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(String friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }

    public String getLinked_user_id() {
        return linked_user_id;
    }

    public void setLinked_user_id(String linked_user_id) {
        this.linked_user_id = linked_user_id;
    }

    public String getFriend_circle_name() {
        return friend_circle_name;
    }

    public void setFriend_circle_name(String friend_circle_name) {
        this.friend_circle_name = friend_circle_name;
    }

    public String getSecret_first_name() {
        return secret_first_name;
    }

    public void setSecret_first_name(String secret_first_name) {
        this.secret_first_name = secret_first_name;
    }

    public String getSecret_last_name() {
        return secret_last_name;
    }

    public void setSecret_last_name(String secret_last_name) {
        this.secret_last_name = secret_last_name;
    }

    public String getSecret_friend_id() {
        return secret_friend_id;
    }

    public void setSecret_friend_id(String secret_friend_id) {
        this.secret_friend_id = secret_friend_id;
    }
}
