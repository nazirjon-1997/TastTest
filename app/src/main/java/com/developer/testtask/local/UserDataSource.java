package com.developer.testtask.local;


import com.developer.testtask.databases.IUserDataSource;
import com.developer.testtask.models.User;

import java.util.List;

import io.reactivex.Flowable;

public class UserDataSource implements IUserDataSource {

    private UserDAO userDAO;
    private static UserDataSource mInstance;

    public UserDataSource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static UserDataSource getInstance(UserDAO userDAO){
        if (mInstance == null){
            mInstance = new UserDataSource(userDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<User>  getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public Flowable<User>  getUserEmailPassword(String email, String password) {
        return userDAO.getUserEmailPassword(email, password);
    }


    @Override
    public Flowable<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void insertUser(User... users) {
        userDAO.insertUser(users);

    }

    @Override
    public void updateUser(User... users) {
        userDAO.updateUser(users);
    }

    @Override
    public void updateUserPass(User users) {
        userDAO.updateUserPass(users);
    }

    @Override
    public void deleteUser(User... users) {
        userDAO.deleteUser(users);
    }

    @Override
    public void deleteAllUsers() {
        userDAO.deleteAllUsers();

    }
}
