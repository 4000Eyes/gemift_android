package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class NotificationCount{

	@SerializedName("message_count")
	private int messageCount;

	public void setMessageCount(int messageCount){
		this.messageCount = messageCount;
	}

	public int getMessageCount(){
		return messageCount;
	}

	@Override
 	public String toString(){
		return 
			"NotificationCount{" + 
			"message_count = '" + messageCount + '\'' + 
			"}";
		}
}