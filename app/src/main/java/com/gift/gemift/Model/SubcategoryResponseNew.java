package com.gift.gemift.Model;

import java.io.Serializable;
import java.util.List;

public class SubcategoryResponseNew implements Serializable {

    private List<Subcategory> subcategory;

    public List<Subcategory> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(List<Subcategory> subcategory) {
        this.subcategory = subcategory;
    }
}
