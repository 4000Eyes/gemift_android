package com.gift.gemift.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FriendCircleAddInterestResponse{

	@SerializedName("subcategories")
	private List<SubcategoriesItem> subcategories;

	public void setSubcategories(List<SubcategoriesItem> subcategories){
		this.subcategories = subcategories;
	}

	public List<SubcategoriesItem> getSubcategories(){
		return subcategories;
	}

	@Override
 	public String toString(){
		return 
			"FriendCircleAddInterestResponse{" + 
			"subcategories = '" + subcategories + '\'' + 
			"}";
		}
}