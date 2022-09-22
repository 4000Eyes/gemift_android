package com.gift.gemift.Storage.DB.DaoAccess;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gift.gemift.Storage.DB.Entity.UserDetailEntity;

import io.reactivex.Flowable;

@Dao
public interface UserDetailDao {
    @Insert
    void insertUserDetail(UserDetailEntity[] userDetailEntities);

    @Insert
    void insertUserDetail(UserDetailEntity userDetailEntities);

    @Update
    void updateUserDetail(UserDetailEntity userDetailEntities);

    @Delete
    void deleteUserDetail(UserDetailEntity userDetailEntities);

    @Query("DELETE FROM db_user_detail")
    void deleteAllUserDetails();

    @Query("SELECT * FROM db_user_detail")
    Flowable<UserDetailEntity[]> getAllUserDetail();

    @Query("UPDATE db_user_detail set image_url= :imagePath  where logged_user_id =:userId ")
    void updateProfileImage(String userId, String imagePath);
}

