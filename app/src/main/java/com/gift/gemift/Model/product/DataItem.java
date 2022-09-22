package com.gift.gemift.Model.product;

public class DataItem{
	private String _id;

	public void setId(String id){
		this._id = id;
	}

	public String getId(){
		return _id;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"_id = '" + _id + '\'' +
			"}";
		}
}
