package com.developer.testtask.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.developer.testtask.models.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users WHERE email=:email")
    Flowable<User>  getUserByEmail(String email);


    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    Flowable<User>  getUserEmailPassword(String email, String password);

    @Query("SELECT * FROM users")
    Flowable<List<User>> getAllUsers();

    @Insert
    void insertUser(User... users);

    @Update
    void updateUser(User... users);

    @Update
    void updateUserPass(User users);

    @Delete
    void deleteUser(User... users);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}
