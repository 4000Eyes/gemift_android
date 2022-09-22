package com.gift.gemift.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyInterestAdded{

	@SerializedName("data")
	private List<MyInterestsItem> myInterests;

	public void setMyInterests(List<MyInterestsItem> myInterests){
		this.myInterests = myInterests;
	}

	public List<MyInterestsItem> getMyInterests(){
		return myInterests;
	}

	@Override
 	public String toString(){
		return 
			"MyInterestAdded{" + 
			"myInterests = '" + myInterests + '\'' + 
			"}";
		}
}