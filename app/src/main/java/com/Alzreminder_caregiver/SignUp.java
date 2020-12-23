/*

HW 4

Course: CMPT 385 Software Engineering
Instructor: Dr. Herbert H. Tsang
Description: <
     THE ACTIVITY ALLOWS YOU TO SIGN UP AND SAVE THE DATA TO THE DATABASE
    >
Due date: < 2020/12/02 >
FILE NAME:SignUp.java
TEAM NAME: Alzreminders
Author: < Kyung Cheol Koh >
Input: < None>
Output: < Initialize the database  >
I pledge that I have completed the programming assignment independently.
I have not copied the code from a student or any source.
I have not given my code to any student.

Sign here: __Kyung Cheol Koh______
*/





package com.Alzreminder_caregiver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends  Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void signingUp(String username, String password, String email, String firstName, String lastName){
        ParseUser user = new ParseUser();
        // FORCE LOGOUT
        user.logOut();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("first_name", firstName);
        user.put("last_name", lastName);
        //BACKGROUND THREAD TO SIGNUP
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(getApplicationContext(),"Sign up Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }


    //CALL THIS FUNCTION WHEN BUTTON IS CLICKED
    public void onClickedSignedUp(View view){
        //UI
        EditText usernameText = findViewById(R.id.username);
        EditText passwordText = findViewById(R.id.password);
        EditText passwordConfirmText = findViewById(R.id.password_confirm);
        EditText firstNameText = findViewById(R.id.first_name);
        EditText lastNameText = findViewById(R.id.last_name);
        EditText emailText = findViewById(R.id.email_address);

        //VARIABLES
        boolean unMatchedPassword = !(passwordText.getText().toString().matches(passwordConfirmText.getText().toString()));
        boolean emptyUsername = usernameText.getText().toString().matches("");
        boolean emptyEmail = emailText.getText().toString().matches("" );

        //IF USERNAME, EMAIL , PASSWORD ARE EMPTY, RETURN FALSE
        boolean  emptyFields = emptyEmail || unMatchedPassword|| emptyUsername;

        // IF THE PASSWORD OR USERNAME IS EMPTY GIVE A TOAST MESSAGE OTHERWISE PROCEED TO LOGIN AND SIGN UP
        if(emptyFields){
            Toast.makeText(this, "Please check again", Toast.LENGTH_SHORT).show();
        }
        else {
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            String email = emailText.getText().toString();
            String firstNameString = firstNameText.getText().toString();
            String lastNameString = lastNameText.getText().toString();

            signingUp(username,password, email, firstNameString, lastNameString);
            finish();
        }
    }
}
