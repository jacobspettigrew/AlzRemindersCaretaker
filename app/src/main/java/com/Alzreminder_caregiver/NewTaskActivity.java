package com.Alzreminder_caregiver;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewTaskActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_Tasks = "com.example.android.patientApp.REPLY";

    private EditText mEditTaskView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        mEditTaskView = findViewById(R.id.edit_Task);

        final Button button = findViewById(R.id.button_save);
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
