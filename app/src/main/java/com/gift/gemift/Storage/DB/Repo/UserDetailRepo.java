package com.gift.gemift.Storage.DB.Repo;

import android.app.Application;
import android.util.Log;

import com.gift.gemift.Storage.DB.DaoAccess.UserDetailDao;
import com.gift.gemift.Storage.DB.DataBase.Gemift_DataBase;
import com.gift.gemift.Storage.DB.Entity.UserDetailEntity;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserDetailRepo {
    private static final String TAG = "SingUpRepo";
    private final UserDetailDao userDetailDao;


    public UserDetailRepo(Application application) {
        Gemift_DataBase userDataBase = Gemift_DataBase.getInstance(application);
        userDetailDao = userDataBase.userDetailDao();
    }

    public void insertUserDetails(final UserDetailEntity[] UserEntities) {
        Completable.fromAction(() -> userDetailDao.insertUserDetail(UserEntities)).subscribeOn(Schedulers.io())
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

    public void insertUserDetails(final UserDetailEntity userDetailEntity) {
        Completable.fromAction(() -> userDetailDao.insertUserDetail(userDetailEntity)).subscribeOn(Schedulers.io())
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

    public void updateUserDetails(final UserDetailEntity userDetailEntity) {
        Completable.fromAction(() -> userDetailDao.updateUserDetail(userDetailEntity)).subscribeOn(Schedulers.io())
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

    public void deleteUserDetail(final UserDetailEntity userDetailEntity) {
        Completable.fromAction(() -> userDetailDao.deleteUserDetail(userDetailEntity)).subscribeOn(Schedulers.io())
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

    public void deleteAllUserDetails() {
        Completable.fromAction(userDetailDao::deleteAllUserDetails).subscribeOn(Schedulers.io())
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

    public void updateProfileImage(String userId, String imagePath) {
        Completable.fromAction(() -> userDetailDao.updateProfileImage(userId, imagePath)).subscribeOn(Schedulers.io())
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

    public Flowable<UserDetailEntity[]> getAllUser() {
        return userDetailDao.getAllUserDetail();
    }
}
