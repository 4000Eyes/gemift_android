package com.gift.gemift.Model;

import java.io.Serializable;

public class FriendSearchModel implements Serializable {

    private FriendDataSearchModel[] data;

    public FriendDataSearchModel[] getData() {
        return data;
    }

    public void setData(FriendDataSearchModel[] data) {
        this.data = data;
    }


}
