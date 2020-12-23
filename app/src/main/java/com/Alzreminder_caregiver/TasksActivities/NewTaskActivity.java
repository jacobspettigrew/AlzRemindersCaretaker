package com.Alzreminder_caregiver.TasksActivities;

/*

HW 4

Course: CMPT 385 Software Engineering
Instructor: Dr. Herbert H. Tsang
Description: <
     TO ADD a new TASK to the Recyclerview
    >
Due date: < 2020/12/02 >
FILE NAME:NewTaskActivity.java
TEAM NAME: Alzreminders
Author: < Kyung Cheol Koh >
Input: < None>
Output: < Initialize the database  >
I pledge that I have completed the programming assignment independently.
I have not copied the code from a student or any source.
I have not given my code to any student.

Sign here: __Kyung Cheol Koh______
*/



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.Alzreminder_caregiver.R;

public class NewTaskActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_Tasks = "com.example.android.patientApp.REPLY";

    private EditText mEditTaskView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        mEditTaskView = findViewById(R.id.edit_Task);

        final Button button = findViewById(R.id.button_save);
        //OnClick listener to send the Task to the MainTask
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTaskView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);

                } else {
                    String Task = mEditTaskView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_Tasks, Task);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
