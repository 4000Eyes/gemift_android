package com.gift.gemift.Storage.DB.DaoAccess;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gift.gemift.Storage.DB.Entity.FriendListEntity;

import io.reactivex.Flowable;

@Dao
public interface FriendListDao {

    @Insert
    void insertFriendListDetail(FriendListEntity[] addressEntity);

    @Insert
    void insertFriendListDetail(FriendListEntity addressEntity);

    @Update
    void updateFriendListDetail(FriendListEntity addressEntity);

    @Delete
    void deleteFriendListDetail(FriendListEntity addressEntity);

    @Query("DELETE FROM db_friend_list")
    void deleteAllFriendListDetails();

    @Query("SELECT * FROM db_friend_list")
    Flowable<FriendListEntity[]> getAllFriendList();

}
