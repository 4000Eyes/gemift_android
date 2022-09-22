package com.gift.gemift.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyCategory{

	@SerializedName("data")
	private List<MyCategoryDataItem> data;

	public void setData(List<MyCategoryDataItem> data){
		this.data = data;
	}

	public List<MyCategoryDataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"MyCategory{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}