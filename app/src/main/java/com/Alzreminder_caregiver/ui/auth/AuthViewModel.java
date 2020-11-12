package com.Alzreminder_caregiver.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.Alzreminder_caregiver.di.ViewModelKey;

import javax.inject.Inject;

import dagger.Binds;
import dagger.multibindings.IntoMap;

public abstract class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    @Inject
    public AuthViewModel() {
        Log.d(TAG, "AuthViewModel: viewmodel is working...");
    }
}
