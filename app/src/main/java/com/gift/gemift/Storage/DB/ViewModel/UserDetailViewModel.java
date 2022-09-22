package com.gift.gemift.Storage.DB.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gift.gemift.Storage.DB.Entity.UserDetailEntity;
import com.gift.gemift.Storage.DB.Repo.UserDetailRepo;

import io.reactivex.Flowable;

public class UserDetailViewModel extends AndroidViewModel {
    private final UserDetailRepo userDetailRepo;

    public UserDetailViewModel(@NonNull Application application) {
        super(application);
        userDetailRepo = new UserDetailRepo(application);
    }

    public void insertFriendListDetails(UserDetailEntity[] userDetailEntities) {
        userDetailRepo.insertUserDetails(userDetailEntities);
    }

    public void insertFriendListDetails(UserDetailEntity userDetailEntities) {
        userDetailRepo.insertUserDetails(userDetailEntities);
    }

    public void updateUserDetails(UserDetailEntity userDetailEntities) {
        userDetailRepo.updateUserDetails(userDetailEntities);
    }

    public void deleteUserDetail(UserDetailEntity userDetailEntities) {
        userDetailRepo.deleteUserDetail(userDetailEntities);
    }

    public void updateProfileImage(String userId, String imagePath) {
        userDetailRepo.updateProfileImage(userId, imagePath);
    }

    public void deleteAllUserDetails() {
        userDetailRepo.deleteAllUserDetails();
    }

    public Flowable<UserDetailEntity[]> getUserDetail() {
        return userDetailRepo.getAllUser();
    }
}
