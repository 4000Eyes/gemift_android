package com.gift.gemift.Storage.AppPreference;

import static com.gift.gemift.Utils.Constant.CATEGORY_LIST;
import static com.gift.gemift.Utils.Constant.COUNTRY_CODE;
import static com.gift.gemift.Utils.Constant.IDENTITY;
import static com.gift.gemift.Utils.Constant.PHONE_NUMBER;
import static com.gift.gemift.Utils.Constant.PROFILE_IMAGE;
import static com.gift.gemift.Utils.Constant.TOKEN_ID;
import static com.gift.gemift.Utils.Constant.USER_ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gift.gemift.Model.GetNewCategoryResponseItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AppPreferences {

    private SharedPreferences mSharedPreferences;

    public AppPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences("gemifit", Context.MODE_PRIVATE);
    }

    public void setTokenId(String tokenId) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(TOKEN_ID, tokenId);
        edit.apply();
    }
    public String getTokenId() {
        return mSharedPreferences.getString(TOKEN_ID, "");

    }


    public void setCategory(List<GetNewCategoryResponseItem> list){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        edit.putString(CATEGORY_LIST, json);
        edit.apply();
    }

    public void clearCategory(){
        SharedPreferences.Editor edit = mSharedPreferences.edit();

        edit.remove(CATEGORY_LIST).commit();

    }

    public List<GetNewCategoryResponseItem> getCategory() {

        Gson gson = new Gson();
        String json = mSharedPreferences.getString(CATEGORY_LIST, "");
        Type ListType = new TypeToken<ArrayList<GetNewCategoryResponseItem>>() {
        }.getType();
        List<GetNewCategoryResponseItem> data = gson.fromJson(json,ListType);
        return data;
    }

    public void setUserId(String userId) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(USER_ID, userId);
        edit.apply();
    }
    public String getUserId() {
        return mSharedPreferences.getString(USER_ID, "");
    }

    public void setIdentifier(String identifier) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(IDENTITY, identifier);
        edit.apply();
    }

    public String getIdentifier() {
        return mSharedPreferences.getString(IDENTITY, "");

    }

    public String getPhoneNumber() {
        return mSharedPreferences.getString(PHONE_NUMBER, "");

    }

    public String getCountryCode() {
        return mSharedPreferences.getString(COUNTRY_CODE, "");

    }

    public void setPhoneNumber(String phoneNumber) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(PHONE_NUMBER, phoneNumber);
        edit.apply();
    }

    public void setPhoneNumberCountryCode(String countryCode) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(COUNTRY_CODE, countryCode);
        edit.apply();
    }

    public String getProfileImage() {
        return mSharedPreferences.getString(PROFILE_IMAGE, "");

    }

    public void setProfileImage(String imagePath) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(PROFILE_IMAGE, imagePath);
        edit.apply();
    }


    public void setFriendCircleSize(int count) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt("FriendCircleSize", count);
        edit.apply();
    }

    public int getFriendCircleSize() {
        return mSharedPreferences.getInt("FriendCircleSize", 0);

    }

    public void clearPreference() {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.clear().commit();
    }
}
