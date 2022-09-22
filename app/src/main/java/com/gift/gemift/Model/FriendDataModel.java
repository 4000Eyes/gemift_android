package com.gift.gemift.Model;

import java.io.Serializable;

public class FriendDataModel implements Serializable {

    private FriendCircleListResponse[] data;


    public FriendCircleListResponse[] getData() {
        return data;
    }

    public void setData(FriendCircleListResponse[] data) {
        this.data = data;
    }
}
