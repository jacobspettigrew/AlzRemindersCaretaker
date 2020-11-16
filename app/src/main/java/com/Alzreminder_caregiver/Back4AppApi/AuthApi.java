package com.Alzreminder_caregiver.Back4AppApi;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.Alzreminder_caregiver.AuthActivity;
import com.Alzreminder_caregiver.Home;
import com.Alzreminder_caregiver.SetId;
import com.Alzreminder_caregiver.models.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import dagger.android.support.DaggerAppCompatActivity;

import static androidx.core.content.ContextCompat.startActivity;

public class AuthApi extends AppCompatActivity {

    public  static int[] authNum;
    private static final String TAG = "AuthApi";
    private AuthApi authApi;


    public AuthApi(){
        Log.d(TAG, "AuthApi: working");
        authNum = new int[1];
    }

    public void authenticate( String username, String password)  {

        ParseUser.logInInBackground(username,password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if(user != null){
                    Log.d(TAG, "done: successful");

                    //Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
                    if(!user.getBoolean("connectToPatient")){
                        authNum[0] = 1;
                        //Log.d(TAG, "done: " + authNum[0]);
//                        Intent intent = new Intent( SetId.class);
//                        setResult(RESULT_OK, intent);
                    }
                    else{
                        authNum[0] = 2;
                        //Log.d(TAG, "done: " + authNum[0]);


                    }
                }
                else {
                    authNum[0] = -1;

                    //Log.d(TAG, "done: unsuccessful" + + authNum[0]);
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


}
