package com.Alzreminder_caregiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainTask extends AppCompatActivity {

    private ArrayList<String> tasks;
    private ArrayAdapter<String> tasksAdapter;
    private ListView taskView;
    private Button addButton;
    private Button deleteButton;
    private Button editButton;
    private Button archiveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_tasks);

        // Find the UI elements by their id
        taskView = findViewById(R.id.taskView);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask(v);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask(v);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask(v);
            }
        });


        // The list that will hold the tasks
        tasks = new ArrayList<>();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskView.setAdapter(tasksAdapter);

    }

    // Deletes the task in the list on a long press
    private void setUpTaskViewListener() {
        taskView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Task Removed", Toast.LENGTH_LONG).show();
                tasks.remove(position);
                tasksAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    // Adds a task to the tasklist
    private void addTask(View v) {
        EditText input = findViewById(R.id.inputTaskEditText);
        String taskText = input.getText().toString();

        if(!(taskText.equals(""))) {
            tasksAdapter.add(taskText);
            input.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter task", Toast.LENGTH_LONG).show();
        }
    }

    // Deletes the task with a long press after delete button is implemented
    // Change to short press
    private void deleteTask(View v) {
        setUpTaskViewListener();
    }

    // Moves the task to the editText to be edited and deletes the old task
    private void taskEditListener() {
        taskView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Editing Task", Toast.LENGTH_LONG).show();

                EditText input = findViewById(R.id.inputTaskEditText);
                input.setText(tasks.get(position));

                tasks.remove(position);
                tasksAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    // Allows the user to edit task
    private void editTask(View v) {
        taskEditListener();
    }
}
