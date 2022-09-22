package com.gift.gemift.Model;

import com.gift.gemift.Storage.DB.Entity.FriendListEntity;

import java.io.Serializable;

public class FriendCircleResponse implements Serializable {

    private FriendListEntity[] friend_circle_id;

    public FriendListEntity[] getFriend_circle_id() {
        return friend_circle_id;
    }

    public void setFriend_circle_id(FriendListEntity[] friend_circle_id) {
        this.friend_circle_id = friend_circle_id;
    }
}
