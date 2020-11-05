package com.Alzreminder_caregiver;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
        if(ParseUser.getCurrentUser() != null){
            goToMainTask();
        }


    }

    public void goToSignUp(View view){
        Toast.makeText(this,"Sign Up", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Sign_up.class);
        startActivity(intent);
    }

    public void goToMainTask(){
        Intent intent = new Intent(this, MainTask.class);
        startActivity(intent);
    }

    public void loggingIn(View view){
        EditText usernameText = findViewById(R.id.usernameLogin);
        EditText passwordText = findViewById(R.id.passwordLogin);
        boolean emptyUsername = usernameText.getText().toString().matches("");
        boolean emptyPassword = passwordText.getText().toString().matches("" );
        boolean  emptyUserPassword = emptyUsername || emptyPassword;

        // if the password or username is empty give a toast message otherwise proceed to login and sign up
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
