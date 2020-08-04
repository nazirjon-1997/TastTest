package com.developer.testtask.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.developer.testtask.models.User;

import static com.developer.testtask.local.UserDatabase.DATABASE_VERSION;

@Database(entities = User.class, version = UserDatabase.DATABASE_VERSION)
public abstract class UserDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="RoomData";

    public abstract UserDAO userDAO();

    public static UserDatabase mInstance;

    public static UserDatabase getInstance(Context context){
        if (mInstance ==null){
            mInstance = Room.databaseBuilder(context,UserDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }


}
