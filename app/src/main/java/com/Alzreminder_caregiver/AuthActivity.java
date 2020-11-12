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

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    @Inject
    Parse.Configuration parseInstance;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    Drawable logo;
    @Inject
    RequestManager requestManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(parseInstance);
        setContentView(R.layout.activity_auth);

        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
        setLogo();

        if(ParseUser.getCurrentUser() != null){
            goToMainTask();
        }

    }


    private void setLogo(){
        requestManager
                .load(logo)
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

    // IF LOGGED IN FIRST TIME, IT GOES TO SETID ACTIVITY
    //OTHERWISE HOME ACTIVITY
    public void goToLogin(View view){
        EditText usernameText = findViewById(R.id.username_loginActivity);
        EditText passwordText = findViewById(R.id.password_loginActivity);
        boolean emptyUsername = usernameText.getText().toString().matches("");
        boolean emptyPassword = passwordText.getText().toString().matches("" );
        boolean  emptyUserPassword = emptyUsername || emptyPassword;

        //IF THE PASSWORD OR USERNAME IS EMPTY GIVE A TOAST MESSAGE OTHERWISE PROCEED TO LOGIN AND SIGN UP
        if(emptyUserPassword){
            Toast.makeText(this, "username and password are required", Toast.LENGTH_SHORT).show();
        }
        else {
            ParseUser.logInInBackground(usernameText.getText().toString(),passwordText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null){
                        Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
                        if(!user.getBoolean("connectToPatient")){
                            Intent intent = new Intent(view.getContext(), SetId.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(view.getContext(), Home.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
