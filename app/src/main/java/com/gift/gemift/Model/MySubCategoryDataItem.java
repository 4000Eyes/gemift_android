package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class MySubCategoryDataItem {

	@SerializedName("web_subcategory_id")
	private String webSubcategoryId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("web_subcategory_name")
	private String webSubcategoryName;

	public void setWebSubcategoryId(String webSubcategoryId){
		this.webSubcategoryId = webSubcategoryId;
	}

	public String getWebSubcategoryId(){
		return webSubcategoryId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setWebSubcategoryName(String webSubcategoryName){
		this.webSubcategoryName = webSubcategoryName;
	}

	public String getWebSubcategoryName(){
		return webSubcategoryName;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"web_subcategory_id = '" + webSubcategoryId + '\'' + 
			",user_id = '" + userId + '\'' + 
			",image_url = '" + imageUrl + '\'' + 
			",web_subcategory_name = '" + webSubcategoryName + '\'' + 
			"}";
		}
}