package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class SubcategoriesItem{

	@SerializedName("subcategory_id")
	private String subcategoryId;

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("subcategory_name")
	private String subcategoryName;

	@SerializedName("votes")
	private int votes;

	@SerializedName("users")
	private int users;

	public void setSubcategoryId(String subcategoryId){
		this.subcategoryId = subcategoryId;
	}

	public String getSubcategoryId(){
		return subcategoryId;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setSubcategoryName(String subcategoryName){
		this.subcategoryName = subcategoryName;
	}

	public String getSubcategoryName(){
		return subcategoryName;
	}

	public void setVotes(int votes){
		this.votes = votes;
	}

	public int getVotes(){
		return votes;
	}

	public void setUsers(int users){
		this.users = users;
	}

	public int getUsers(){
		return users;
	}

	@Override
 	public String toString(){
		return 
			"SubcategoriesItem{" + 
			"subcategory_id = '" + subcategoryId + '\'' + 
			",image_url = '" + imageUrl + '\'' + 
			",subcategory_name = '" + subcategoryName + '\'' + 
			",votes = '" + votes + '\'' + 
			",users = '" + users + '\'' + 
			"}";
		}
}