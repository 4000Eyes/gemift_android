package com.gift.gemift.Model;

import java.io.Serializable;

public class SubcategoryResponse implements Serializable {

    private Subcategory[] subcategory;

    public Subcategory[] getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory[] subcategory) {
        this.subcategory = subcategory;
    }
}
