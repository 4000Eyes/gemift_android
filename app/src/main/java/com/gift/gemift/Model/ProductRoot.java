package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductRoot implements Serializable {

    public ArrayList<ProductDatum> data;

    public ArrayList<ProductDatum> getData() {
        return data;
    }

    public void setData(ArrayList<ProductDatum> data) {
        this.data = data;
    }

    public class Id {
        @SerializedName("$oid")
        public String oid;
    }

}
