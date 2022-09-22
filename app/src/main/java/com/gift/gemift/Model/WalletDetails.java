package com.gift.gemift.Model;

import java.io.Serializable;
import java.util.List;

public class WalletDetails implements Serializable {
    private List<WalletMessage> message;

    public List<WalletMessage> getMessage() {
        return message;
    }

    public void setMessage(List<WalletMessage> message) {
        this.message = message;
    }
}
