package com.Alzreminder_caregiver.ui.auth;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Alzreminder_caregiver.Back4AppApi.AuthApi;
import com.Alzreminder_caregiver.Home;
import com.Alzreminder_caregiver.SetId;
//import com.Alzreminder_caregiver.di.ViewModelKey;
import com.Alzreminder_caregiver.SignUp;
import com.Alzreminder_caregiver.models.User;
import com.Alzreminder_caregiver.repositories.UserRepository;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

import javax.inject.Inject;

import dagger.Binds;
import dagger.multibindings.IntoMap;

import static androidx.core.content.ContextCompat.startActivity;
import static com.Alzreminder_caregiver.Back4AppApi.AuthApi.authNum;

public class AuthViewModel extends AndroidViewModel  {

    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;
    private MediatorLiveData<User> authUser = new MediatorLiveData<>();
    private UserRepository mUserRepository;
    Application application;

    @Inject
    public AuthViewModel(Application application) {
        super(application);
        this.application = application;
        this.authApi = new AuthApi();
        Log.d(TAG, "AuthViewModel: viewmodel is working...");

    }

//    public LiveData<User> observeUser(){
//        return authUser;
//    }

    public void authWithUsernamePassword(String username, String password) {


        authApi.authenticate(username,password);



        Log.d(TAG, "authWithUsernamePassword: " + authNum[0] );
    }
//
//        //user.setValue(authApi.getUser(username,password));
//    }

//    public LiveData<User> getUser(){
//        return mUserRepository.getUser();
//    }


}