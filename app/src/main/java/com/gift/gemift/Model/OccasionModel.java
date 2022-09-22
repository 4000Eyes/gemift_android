package com.gift.gemift.Model;

import java.io.Serializable;

public class OccasionModel implements Serializable {

    private OccasionListResponse[] occasion_name;

    public OccasionListResponse[] getOccasion_name() {
        return occasion_name;
    }

    public void setOccasion_name(OccasionListResponse[] occasion_name) {
        this.occasion_name = occasion_name;
    }
}
