package com.gift.gemift.Storage.DB.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gift.gemift.Storage.DB.Entity.FriendListEntity;
import com.gift.gemift.Storage.DB.Repo.FriendListRepo;

import io.reactivex.Flowable;

public class FriendListViewModel extends AndroidViewModel {

    private final FriendListRepo friendListRepo;

    public FriendListViewModel(@NonNull Application application) {
        super(application);
        friendListRepo = new FriendListRepo(application);
    }

    public void insertFriendListDetails(FriendListEntity[] FriendListEntities) {
        friendListRepo.insertFriendListDetails(FriendListEntities);
    }

    public void insertFriendListDetails(FriendListEntity FriendListEntities) {
        friendListRepo.insertFriendListDetails(FriendListEntities);
    }

    public void updateFriendListDetails(FriendListEntity FriendListEntities) {
        friendListRepo.updateUserDetails(FriendListEntities);
    }

    public void deleteFriendListDetails(FriendListEntity FriendListEntities) {
        friendListRepo.deleteUserDetail(FriendListEntities);
    }

    public void deleteAllFriendListDetails() {
        friendListRepo.deleteAllAddress();
    }

    public Flowable<FriendListEntity[]> getAllFriendList() {
        return friendListRepo.getAllFriendList();
    }
}
