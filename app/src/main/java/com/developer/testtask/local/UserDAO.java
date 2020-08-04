package com.developer.testtask.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.developer.testtask.models.User;


import io.reactivex.Flowable;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users WHERE email=:email")
    Flowable<User>  getUserByEmail(String email);


    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    Flowable<User>  getUserEmailPassword(String email, String password);

    @Insert
    void insertUser(User... users);

    @Update
    void updateUser(User... users);

    @Update
    void updateUserPass(User users);
}
