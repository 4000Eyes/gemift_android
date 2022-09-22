package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

public class FriendCircleDataItem{

	@SerializedName("info_type")
	private String infoType;

	@SerializedName("current_user_score")
	private double currentUserScore;

	@SerializedName("friend_circle_id")

	public double getCurrentUserScore(){
		return currentUserScore;
	}

	public String getFriendCircleId(){
		return friendCircleId;
	}

	public double getFriendCircleScore(){
		return friendCircleScore;
	}
	private String friendCircleId;

	@SerializedName("friend_circle_score")
	private double friendCircleScore;

	public String getInfoType(){
		return infoType;
	}
}