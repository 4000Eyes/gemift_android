package com.gift.gemift.Model.product;

import java.util.List;

public class ProductCategoryResponse{
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ProductCategoryResponse{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}