package com.gift.gemift.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyOccasionResponse{

	@SerializedName("data")
	private List<MyOccasionDataItem> myOccasionData;




	public void setMyOccasionData(List<MyOccasionDataItem> myOccasionData){
		this.myOccasionData = myOccasionData;
	}

	public List<MyOccasionDataItem> getMyOccasionData(){
		return myOccasionData;
	}

	@Override
 	public String toString(){
		return 
			"MyOccasionResponse{" + 
			"myOccasionData = '" + myOccasionData + '\'' + 
			"}";
		}
}