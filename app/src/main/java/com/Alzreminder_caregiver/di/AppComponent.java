package com.Alzreminder_caregiver.di;

import android.app.Application;

import com.Alzreminder_caregiver.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(
        modules = {AndroidInjectionModule.class, ActivityBuildersModule.class, AppModule.class, ViewModelFactoryModule.class}
)
public interface AppComponent extends AndroidInjector<BaseApplication> {


    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
