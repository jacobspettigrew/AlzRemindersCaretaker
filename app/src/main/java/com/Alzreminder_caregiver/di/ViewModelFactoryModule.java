package com.Alzreminder_caregiver.di;

import androidx.lifecycle.ViewModelProvider;

import com.Alzreminder_caregiver.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

}
