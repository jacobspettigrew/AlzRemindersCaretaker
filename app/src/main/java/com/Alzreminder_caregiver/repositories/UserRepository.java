package com.Alzreminder_caregiver.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Alzreminder_caregiver.models.User;

public class UserRepository {

    private static UserRepository instance;
    private MutableLiveData<User> mUser;

    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository (){
        mUser = new MutableLiveData<>();
    }

    public LiveData<User> getUser(){
        return mUser;
    }
//    private LiveData<User>  authenticateUser(User){
//
//    }
}
