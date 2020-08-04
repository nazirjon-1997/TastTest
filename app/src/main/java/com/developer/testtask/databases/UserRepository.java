package com.developer.testtask.databases;

import com.developer.testtask.models.User;

import io.reactivex.Flowable;

public class UserRepository implements IUserDataSource {

    private IUserDataSource mLocalDataSource;
    private static UserRepository mInstance;

    public UserRepository(IUserDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getInstance(IUserDataSource mLocalDataSource){
        if (mInstance == null){
            mInstance = new UserRepository(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public Flowable<User>  getUserByEmail(String email) {
        return mLocalDataSource.getUserByEmail(email);
    }

    @Override
    public Flowable<User> getUserEmailPassword(String email, String password) {
        return mLocalDataSource.getUserEmailPassword(email, password);
    }

    @Override
    public void insertUser(User... users) {
        mLocalDataSource.insertUser(users);
    }

    @Override
    public void updateUser(User... users) {
        mLocalDataSource.updateUser(users);
    }

    @Override
    public void updateUserPass(User users) {
        mLocalDataSource.updateUserPass(users);
    }

}
