package com.gift.gemift.Model;

import java.io.Serializable;
import java.util.Comparator;

public class ProductDatum implements Serializable {

/*mr    public static Comparator<ProductDatum> nameComparator = new Comparator<ProductDatum>() {
        @Override
        public int compare(ProductDatum o1, ProductDatum o2) {
            return o1.getOccasion_name().compareTo(o2.getOccasion_name());
        }
    };*/
//.    public static Comparator<ProductDatum> sizeComparator = new Comparator<ProductDatum>() {
//        @Override
//        public int compare(ProductDatum o1, ProductDatum o2) {
//            return o1.getSize() - o2.getSize();
//        }
//    };
    public static Comparator<ProductDatum> priceComparator = new Comparator<ProductDatum>() {
        @Override
        public int compare(ProductDatum o1, ProductDatum o2) {
            return (int) (o1.getPrice() - o2.getPrice());
        }
    };
    public ProductRoot.Id _id;
    public int product_id;
    public String product_title;
    public String product_description;
    public String category;
    public String subcategory;
    public double price;
    public String gender;
    public int age_lo;
    public int age_hi;
    public String website_url;
    public String seasonal_product;
   /*mr public String occasion_name;
    public String occasion_id;*/
    public String country;

    public ProductRoot.Id get_id() {
        return _id;
    }

    public void set_id(ProductRoot.Id _id) {
        this._id = _id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge_lo() {
        return age_lo;
    }

    public void setAge_lo(int age_lo) {
        this.age_lo = age_lo;
    }

    public int getAge_hi() {
        return age_hi;
    }

    public void setAge_hi(int age_hi) {
        this.age_hi = age_hi;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public String getSeasonal_product() {
        return seasonal_product;
    }

    public void setSeasonal_product(String seasonal_product) {
        this.seasonal_product = seasonal_product;
    }

/*mr
    public String getOccasion_name() {
        return occasion_name;
    }

    public void setOccasion_name(String occasion_name) {
        this.occasion_name = occasion_name;
    }

    public String getOccasion_id() {
        return occasion_id;
    }

    public void setOccasion_id(String occasion_id) {
        this.occasion_id = occasion_id;
    }
*/

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
