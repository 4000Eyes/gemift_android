package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class Status{

	@SerializedName("vonage_request_id")
	private String vonageRequestId;

	public void setVonageRequestId(String vonageRequestId){
		this.vonageRequestId = vonageRequestId;
	}

	public String getVonageRequestId(){
		return vonageRequestId;
	}

	@Override
 	public String toString(){
		return 
			"Status{" + 
			"vonage_request_id = '" + vonageRequestId + '\'' + 
			"}";
		}
}