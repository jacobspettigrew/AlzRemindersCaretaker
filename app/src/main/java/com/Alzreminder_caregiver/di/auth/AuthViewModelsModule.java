package com.Alzreminder_caregiver.di.auth;


import androidx.lifecycle.ViewModel;

import com.Alzreminder_caregiver.di.ViewModelKey;
import com.Alzreminder_caregiver.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
