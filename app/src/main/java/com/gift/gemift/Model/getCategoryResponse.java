package com.gift.gemift.Model;

import java.io.Serializable;

public class getCategoryResponse implements Serializable {

    private getCategoryDetail[] categories;

    public getCategoryDetail[] getCategories() {
        return categories;
    }

    public void setCategories(getCategoryDetail[] categories) {
        this.categories = categories;
    }
}
