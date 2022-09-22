package com.gift.gemift.Model;

import java.io.Serializable;

public class GetOccasionResponse implements Serializable {

    private GetOccasionDetailResponse[] occasions;

    public GetOccasionDetailResponse[] getOccasions() {
        return occasions;
    }

    public void setOccasions(GetOccasionDetailResponse[] occasions) {
        this.occasions = occasions;
    }
}
