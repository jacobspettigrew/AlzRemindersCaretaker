package com.Alzreminder_caregiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class App extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );

        setContentView(R.layout.activity_main);
//        if(ParseUser.getCurrentUser() != null){
//            goToMainTask();
//        }
    }

    public void goToMainTask(View view){
        Intent intent = new Intent(this, MainTask.class);
        startActivity(intent);
    }



    public void switch_sign_up(View view){
        Toast.makeText(App.this,"Logged in", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Sign_up.class);
        startActivity(intent);
    }


    public void loggingIn(View view){

//        ParseUser user = new ParseUser();
//        user.logOut();
        EditText usernameText = findViewById(R.id.usernameLogin);
        EditText passwordText = findViewById(R.id.passwordLogin);

       boolean  empty_user_pass = usernameText.getText().toString().matches("") || passwordText.getText().toString().matches("" );

        // if the password or username is empty give a toast message otherwise proceed to login and sign up
        if(empty_user_pass){
            Toast.makeText(this, "username and password are required", Toast.LENGTH_SHORT).show();
        }
        else {
            ParseUser.logInInBackground(usernameText.getText().toString(),passwordText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null){

                        Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), MainTask.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}
