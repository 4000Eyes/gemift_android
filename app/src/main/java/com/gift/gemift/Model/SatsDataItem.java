package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class SatsDataItem{

	@SerializedName("total_friend_circles")
	private int totalFriendCircles;

	@SerializedName("total_occasions")
	private int totalOccasions;

	@SerializedName("total_interest_count")
	private int totalInterestCount;

	public int getTotalFriendCircles(){
		return totalFriendCircles;
	}

	public int getTotalOccasions(){
		return totalOccasions;
	}

	public int getTotalInterestCount(){
		return totalInterestCount;
	}
}