package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class GetNewCategoryResponseItem{

	@SerializedName("web_category_id")
	private String webCategoryId;

	@SerializedName("web_category_name")
	private String webCategoryName;

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
			"GetNewCategoryResponseItem{" + 
			"web_category_id = '" + webCategoryId + '\'' + 
			",web_category_name = '" + webCategoryName + '\'' + 
			"}";
		}
}