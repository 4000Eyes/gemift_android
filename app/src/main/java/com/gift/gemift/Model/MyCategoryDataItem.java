package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class MyCategoryDataItem {

	@SerializedName("user_id")
	private String userId;

	@SerializedName("image_url")
	private Object imageUrl;

	@SerializedName("web_category_id")
	private String webCategoryId;

	@SerializedName("web_category_name")
	private String webCategoryName;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setImageUrl(Object imageUrl){
		this.imageUrl = imageUrl;
	}

	public Object getImageUrl(){
		return imageUrl;
	}

	public void setWebCategoryId(String webCategoryId){
		this.webCategoryId = webCategoryId;
	}

	public String getWebCategoryId(){
		return webCategoryId;
	}

	public void setWebCategoryName(String webCategoryName){
		this.webCategoryName = webCategoryName;
	}

	public String getWebCategoryName(){
		return webCategoryName;
	}

	@Override
 	public String toString(){
		return
			"DataItem{" +
			"user_id = '" + userId + '\'' +
			",image_url = '" + imageUrl + '\'' +
			",web_category_id = '" + webCategoryId + '\'' +
			",web_category_name = '" + webCategoryName + '\'' +
			"}";
		}
}