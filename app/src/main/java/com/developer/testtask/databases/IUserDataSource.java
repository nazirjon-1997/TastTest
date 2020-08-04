package com.developer.testtask.databases;

import com.developer.testtask.models.User;

import io.reactivex.Flowable;

public interface IUserDataSource {

    Flowable<User> getUserByEmail(String email);

    Flowable<User>  getUserEmailPassword(String email, String password);

    void insertUser(User... users);

    void updateUser(User... users);

    void updateUserPass(User users);
}
