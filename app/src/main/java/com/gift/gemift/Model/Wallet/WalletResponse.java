package com.gift.gemift.Model.Wallet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WalletResponse{

	@SerializedName("message")
	private List<MessageItem> message;

	public void setMessage(List<MessageItem> message){
		this.message = message;
	}

	public List<MessageItem> getMessage(){
		return message;
	}
}