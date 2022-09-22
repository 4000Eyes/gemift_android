package com.gift.gemift.Storage.DB.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gift.gemift.Storage.DB.DaoAccess.FriendListDao;
import com.gift.gemift.Storage.DB.DaoAccess.UserDetailDao;
import com.gift.gemift.Storage.DB.Entity.FriendListEntity;
import com.gift.gemift.Storage.DB.Entity.UserDetailEntity;

@Database(entities = {FriendListEntity.class, UserDetailEntity.class}, version = 2, exportSchema = false)
public abstract class Gemift_DataBase extends RoomDatabase {

    private static final Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static Gemift_DataBase instance;

    public static synchronized Gemift_DataBase getInstance(Context context) {
        if (instance == null) {
            //If instance is null that's mean database is not created and create new database
            instance = Room.databaseBuilder(context.getApplicationContext(), Gemift_DataBase.class, "Gemift")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    public abstract FriendListDao friendListDao();

    public abstract UserDetailDao userDetailDao();

}
