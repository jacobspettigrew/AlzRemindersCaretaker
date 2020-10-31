package com.Alzreminder_caregiver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private CardView cardViewTask, cardViewReminder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home);

        cardViewTask = findViewById(R.id.cardViewTask);
        cardViewReminder = findViewById(R.id.cardViewReminder);
        cardViewTask.setOnClickListener(this);
        cardViewReminder.setOnClickListener(this);
    }

    public void goToTasks(View view){
        Toast.makeText(this,"Sign Up", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(), MainTask.class);
        startActivity(intent);
    }

    public void goToReminders(View view){
        Toast.makeText(this,"Sign Up", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(), Reminder.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){
            case R.id.cardViewTask: {
                intent = new Intent(this, MainTask.class);
                startActivity(intent);
                break;
            }
            case R.id.cardViewReminder: {
                intent = new Intent(this, Reminder.class);
                startActivity(intent);
                break;
            }
            default:{
                break;
            }

        }

    }
}
