package com.developer.testtask.databases;

import com.developer.testtask.models.User;

import java.util.List;

import io.reactivex.Flowable;

public interface IUserDataSource {

    Flowable<User> getUserByEmail(String email);

    Flowable<User>  getUserEmailPassword(String email, String password);

    Flowable<List<User>> getAllUsers();

    void insertUser(User... users);

    void updateUser(User... users);

    void updateUserPass(User users);

    void deleteUser(User... users);

    void deleteAllUsers();
}
