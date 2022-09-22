package com.gift.gemift.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StatsModel {

	@SerializedName("data")
	private List<SatsDataItem> satsData;

	public List<SatsDataItem> getSatsData(){
		return satsData;
	}
}