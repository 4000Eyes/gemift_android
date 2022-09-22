package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class SignUpOTPresponse{

	@SerializedName("status")
	private Status status;

	public void setStatus(Status status){
		this.status = status;
	}

	public Status getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"SignUpOTPresponse{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}