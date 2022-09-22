package com.gift.gemift.Model.Wallet;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TransactionItem implements Serializable {

	@SerializedName("detail")
	private List<DetailItem> detail;

	@SerializedName("header")
	private Header header;

	public void setDetail(List<DetailItem> detail){
		this.detail = detail;
	}

	public List<DetailItem> getDetail(){
		return detail;
	}

	public void setHeader(Header header){
		this.header = header;
	}

	public Header getHeader(){
		return header;
	}

	@Override
 	public String toString(){
		return 
			"TransactionItem{" + 
			"detail = '" + detail + '\'' + 
			",header = '" + header + '\'' + 
			"}";
		}
}