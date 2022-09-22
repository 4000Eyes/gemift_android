package com.gift.gemift.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetNewCategoryResponse implements Serializable {

	@SerializedName("GetNewCategoryResponse")
	private List<GetNewCategoryResponseItem> getNewCategoryResponse;

	public void setGetNewCategoryResponse(List<GetNewCategoryResponseItem> getNewCategoryResponse){
		this.getNewCategoryResponse = getNewCategoryResponse;
	}

	public List<GetNewCategoryResponseItem> getGetNewCategoryResponse(){
		return getNewCategoryResponse;
	}

	@Override
 	public String toString(){
		return 
			"GetNewCategoryResponse{" + 
			"getNewCategoryResponse = '" + getNewCategoryResponse + '\'' + 
			"}";
		}
}