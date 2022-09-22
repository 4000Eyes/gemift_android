package com.gift.gemift.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse{

	@SerializedName("status")
	private List<StatusItem> status;

	public void setStatus(List<StatusItem> status){
		this.status = status;
	}

	public List<StatusItem> getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"NotificationResponse{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}