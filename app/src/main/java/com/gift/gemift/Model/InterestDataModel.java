package com.gift.gemift.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class InterestDataModel implements Serializable {

    private InterestList interestList;

    public InterestList getInterestList() {
        return interestList;
    }

    public void setInterestList(InterestList interestList) {
        this.interestList = interestList;
    }
}
