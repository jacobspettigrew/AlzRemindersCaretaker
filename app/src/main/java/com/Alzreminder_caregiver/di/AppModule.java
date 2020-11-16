package com.Alzreminder_caregiver.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.Alzreminder_caregiver.Back4AppApi.AuthApi;
import com.Alzreminder_caregiver.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.parse.Parse;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Parse.Configuration provideParseConfig(Application application){
        return new Parse.Configuration.Builder(application)
                .applicationId("ppUUKGXynkwsUpbZRR6bij1SYvqea45AMYUj70xJ")
                // if defined
                .clientKey("ufwwEJ1t3ERojL7sCNQRL1sk7MRFnwpSUBCLE3sQ")
                .server("https://parseapi.back4app.com")
                .build();
    }


    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions
                .placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance (Application application, RequestOptions requestOptions){
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application){
        return ContextCompat.getDrawable(application,R.drawable.app_icon);
    }
}
