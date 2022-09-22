package com.gift.gemift.Model;

import java.util.List;

public class FriendCircleMatch{
	private List<FriendCircleDataItem> data;

	public void setData(List<FriendCircleDataItem> data){
		this.data = data;
	}

	public List<FriendCircleDataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"FriendCircleMatch{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}