package com.gift.gemift.Storage.DB.Repo;

import android.app.Application;
import android.util.Log;

import com.gift.gemift.Storage.DB.DaoAccess.FriendListDao;
import com.gift.gemift.Storage.DB.DataBase.Gemift_DataBase;
import com.gift.gemift.Storage.DB.Entity.FriendListEntity;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FriendListRepo {

    private static final String TAG = "FriendListRepo";
    private final FriendListDao friendListDao;


    public FriendListRepo(Application application) {
        Gemift_DataBase userDataBase = Gemift_DataBase.getInstance(application);
        friendListDao = userDataBase.friendListDao();
    }

    public void insertFriendListDetails(final FriendListEntity[] FriendListEntities) {
        Completable.fromAction(() -> friendListDao.insertFriendListDetail(FriendListEntities)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    public void insertFriendListDetails(final FriendListEntity friendListEntity) {
        Completable.fromAction(() -> friendListDao.insertFriendListDetail(friendListEntity)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    public void updateUserDetails(final FriendListEntity friendListEntity) {
        Completable.fromAction(() -> friendListDao.updateFriendListDetail(friendListEntity)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called");
                    }
                });
    }

    public void deleteUserDetail(final FriendListEntity friendListEntity) {
        Completable.fromAction(() -> friendListDao.deleteFriendListDetail(friendListEntity)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: Called");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    public void deleteAllAddress() {
        Completable.fromAction(friendListDao::deleteAllFriendListDetails).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called" + e.getMessage());
                    }
                });
    }

    public Flowable<FriendListEntity[]> getAllFriendList() {
        return friendListDao.getAllFriendList();
    }
}
