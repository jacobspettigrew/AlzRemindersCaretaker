/*
HEADER
FILE NAME:App.java
TEAM NAME: Alzreminders
BUGS:
PEOPLE WHO WORKED ON: KYUNG CHEOL KOH
PURPOSE:
    CONNECT TO Back4App service
    OPEN THE MAIN ACTIVITY XML
    LISTS OF FUNCTIONS TO DIRECT TO DIFFERENT ACTIVITEIS

CODING STANDARD
    NAME CONVENTION: CAMELCASE STARTING WITH LOWERCASE
    GLOBAL VARIABLE: CAMELCASE STARTING WITH m

*/


package com.Alzreminder_caregiver;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProviders;

import com.Alzreminder_caregiver.ui.auth.AuthViewModel;
import com.Alzreminder_caregiver.viewmodels.ViewModelProviderFactory;
import com.bumptech.glide.RequestManager;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static androidx.core.content.ContextCompat.startActivity;
import static com.Alzreminder_caregiver.Back4AppApi.AuthApi.authNum;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    private AuthViewModel mViewModel;

    private EditText usernameText;
    private EditText passwordText;

    @Inject
    Parse.Configuration provideParseConfig;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    Drawable provideLogo;
    @Inject
    RequestManager provideRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(provideParseConfig);
        setContentView(R.layout.activity_auth);

        mViewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
        setLogo();

        usernameText = findViewById(R.id.username_loginActivity);
        passwordText = findViewById(R.id.password_loginActivity);

        findViewById(R.id.login_button).setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){
            goToMainTask();
        }

    }


    private void setLogo(){
        provideRequestManager
                .load(provideLogo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

    //LISTS OF FUNCTNIOS TO GO TO DIFFERENT ACTIVITIES
    public void goToSignUp(View view){
        Toast.makeText(this,"Sign Up", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void goToMainTask(){
        Intent intent = new Intent(this, MainTask.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:{
                goToLogin();
            }
        }
    }

    private void goToLogin(){
        boolean emptyUsername = usernameText.getText().toString().matches("");
        boolean emptyPassword = passwordText.getText().toString().matches("" );
        boolean  emptyUserPassword = emptyUsername || emptyPassword;

        //IF THE PASSWORD OR USERNAME IS EMPTY GIVE A TOAST MESSAGE OTHERWISE PROCEED TO LOGIN AND SIGN UP
        if(emptyUserPassword){
            Toast.makeText(this, "username and password are required", Toast.LENGTH_SHORT).show();
        }
        else {
            mViewModel.authWithUsernamePassword(usernameText.getText().toString(),passwordText.getText().toString());
            int num = authNum[0];
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Log.d(TAG, "run: " + authNum[0]);

                    logInOption(authNum[0]);
                }
            }, 1000);
        }
    }

    public void logInOption(int num){
        switch (num){
            case 1:{
                Intent intent = new Intent(this, SetId.class);
                startActivity(intent);
                break;
            }
            case 2:{
                Intent intent = new Intent(this, MainTask.class);
                startActivity(intent);
                break;
            }
            case -1:{
                Log.d(TAG, "logInOption: erroroccured");
                Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();
                break;
            }
            default:{
                Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // IF LOGGED IN FIRST TIME, IT GOES TO SETID ACTIVITY
    //OTHERWISE HOME ACTIVITY
//    public void goToLogin(View view){
//        EditText usernameText = findViewById(R.id.username_loginActivity);
//        EditText passwordText = findViewById(R.id.password_loginActivity);
//        boolean emptyUsername = usernameText.getText().toString().matches("");
//        boolean emptyPassword = passwordText.getText().toString().matches("" );
//        boolean  emptyUserPassword = emptyUsername || emptyPassword;
//
//        //IF THE PASSWORD OR USERNAME IS EMPTY GIVE A TOAST MESSAGE OTHERWISE PROCEED TO LOGIN AND SIGN UP
//        if(emptyUserPassword){
//            Toast.makeText(this, "username and password are required", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            ParseUser.logInInBackground(usernameText.getText().toString(),passwordText.getText().toString(), new LogInCallback() {
//                @Override
//                public void done(ParseUser user, ParseException e) {
//                    if(user != null){
//                        Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
//                        if(!user.getBoolean("connectToPatient")){
//                            Intent intent = new Intent(view.getContext(), SetId.class);
//                            startActivity(intent);
//                        }
//                        else{
//                            Intent intent = new Intent(view.getContext(), Home.class);
//                            startActivity(intent);
//                        }
//                    }
//                    else {
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
}
