package com.Alzreminder_caregiver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class SetId extends AppCompatActivity {
    EditText uniqueId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caregiver_set_id);
        uniqueId = findViewById(R.id.SetIdTextView);
    }

    public void checkUniqueId(View view){
        String uniqueIdText = uniqueId.getText().toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");

        //iterations to find unique id
        query.whereEqualTo("uniqueId", uniqueIdText);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if (objects.size() > 0){
                        for(ParseObject object :objects){
                            if(object.getBoolean("connectToUser")){
                                toastMsgUserExist();
                            }
                            else {
                                object.put("connectToUser", true);
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null){
                                            ParseUser.getCurrentUser().put("patientId", object.getObjectId());
                                            ParseUser.getCurrentUser().put("connectToPatient", true);
                                            ParseUser.getCurrentUser().saveInBackground();
                                            toastMsgMatchSuccess(view);
                                        }
                                        else{
                                        }
                                    }
                                });

                            }
                        }
                    }
                }
            }
        });
    }

    public void goToHome(View view){
        Intent intent = new Intent(view.getContext(), Home.class);
        startActivity(intent);
    }

    public void toastMsgUserExist(){
        Toast.makeText(this, "the id is already connected to another user" , Toast.LENGTH_SHORT).show();
    }

    public void toastMsgMatchSuccess(View view){
            Toast.makeText(this, "match successfully" , Toast.LENGTH_SHORT).show();
            goToHome(view);
    }

}