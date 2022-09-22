package com.gift.gemift.Model.Wallet;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TransactionDetailResponse implements Serializable {

	@SerializedName("message")
	private List<TransactionItem> transaction;

	public void setTransaction(List<TransactionItem> transaction){
		this.transaction = transaction;
	}

	public List<TransactionItem> getTransaction(){
		return transaction;
	}

	@Override
 	public String toString(){
		return 
			"TransactionDetailResponse{" + 
			"transaction = '" + transaction + '\'' + 
			"}";
		}
}