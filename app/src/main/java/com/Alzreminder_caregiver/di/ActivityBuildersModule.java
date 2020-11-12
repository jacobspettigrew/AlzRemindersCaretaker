package com.Alzreminder_caregiver.di;



import com.Alzreminder_caregiver.AuthActivity;
import com.Alzreminder_caregiver.di.auth.AuthViewModelsModule;


import dagger.Module;


import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class}
    )
    abstract AuthActivity contributeAuthActivity();


}
