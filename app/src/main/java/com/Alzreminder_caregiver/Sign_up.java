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

public class Sign_up extends  Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

    }




    public void signingUp(String username, String password, String email, String firstName, String lastName){
        ParseUser user = new ParseUser();
        //if logout forced
        user.logOut();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("first_name", firstName);
        user.put("last_name", lastName);

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


    //need more testing and functinoalities here
    public void onClickedSignedUp(View view){

        EditText usernameText = findViewById(R.id.username);
        EditText passwordText = findViewById(R.id.password);
        EditText passwordConfirmText = findViewById(R.id.password_confirm);
        EditText firstNameText = findViewById(R.id.first_name);
        EditText lastNameText = findViewById(R.id.last_name);
        EditText emailText = findViewById(R.id.email_address);

        boolean unMatchedPassword = !(passwordText.getText().toString().matches(passwordConfirmText.getText().toString()));
        boolean emptyUsername = usernameText.getText().toString().matches("");
        boolean emptyEmail = emailText.getText().toString().matches("" );

        //if username, email , password are empty, return false
        boolean  emptyFields = emptyEmail || unMatchedPassword|| emptyUsername;


        //if password empty
/*        if(passwordText.getText().toString().matches(passwordConfirmText.getText().toString())){
            passwordMatched = false;
        }
        else {
            passwordMatched = true;
        }
        */


        // if the password or username is empty give a toast message otherwise proceed to login and sign up
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
