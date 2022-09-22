package com.gift.gemift.Model;

import java.io.Serializable;

public class SecretFriendAgeResponse implements Serializable {

    private SecretFriendAgeDetail[] status;

    public SecretFriendAgeDetail[] getStatus() {
        return status;
    }

    public void setStatus(SecretFriendAgeDetail[] status) {
        this.status = status;
    }
}
