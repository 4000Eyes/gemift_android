package com.gift.gemift.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MySubCategory{

	@SerializedName("data")
	private List<MySubCategoryDataItem> data;

	public void setData(List<MySubCategoryDataItem> data){
		this.data = data;
	}

	public List<MySubCategoryDataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"MySubCategory{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}