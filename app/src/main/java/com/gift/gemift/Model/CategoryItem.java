package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class CategoryItem{

	@SerializedName("web_category_id")
	private String webCategoryId;

	@SerializedName("web_category_name")
	private String webCategoryName;

	public String getWebCategoryId(){
		return webCategoryId;
	}

	public String getWebCategoryName(){
		return webCategoryName;
	}
}