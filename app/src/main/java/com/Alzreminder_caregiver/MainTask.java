/*
HEADER
FILE NAME: MainTask.java
TEAM NAME: Alzreminders
BUGS:
PEOPLE WHO WORKED ON: KYUNG CHEOL KOH JACOB PETTIGREW
PURPOSE:
        TO ADD, DELETE AND EDIT TASKS FROM THE LISTVIEW
CODING STANDARD
    NAME CONVENTION: CAMELCASE STARTING WITH LOWERCASE
    GLOBAL VARIABLE: CAMELCASE STARTING WITH m
*/


package com.Alzreminder_caregiver;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



import com.parse.FindCallback;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;

public class MainTask extends AppCompatActivity {

    //LISTVIEW
    private ArrayAdapter<String> tasksAdapter;
    ParseObject patientObject;

    //UIz
    private ArrayList<String> mTasks;
    private ListView mTaskView;
    private Button mAddButton;
    private Button mDeleteButton;
    private Button mEditButton;
    private EditText input;
    private EditText dateTimeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carevier_tasks);

        //UI
        mTaskView = findViewById(R.id.taskView);
        mAddButton = findViewById(R.id.addButton);
        mDeleteButton = findViewById(R.id.deleteButton);
        mEditButton = findViewById(R.id.editButton);
        dateTimeInput =findViewById(R.id.dateTimeInput);
        dateTimeInput.setInputType(InputType.TYPE_NULL);

        //LISTVIEW
        mTasks = new ArrayList<>();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTasks);

        findObjectId();
        mTaskView.setAdapter(tasksAdapter);
        setUpTaskViewListener();

        //ONCLICK LISTENERS
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask(v);
                showDateTimeDialog(dateTimeInput);
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask(v);
            }
        });
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask(v);
            }
        });
    }

    // WHEN CALLED, IT WILL ADD THE TASK TO THE LIST
    private void addItem(View view) {
        //UI
        input = findViewById(R.id.inputTaskEditText);
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))) {
            tasksAdapter.add(itemText);
            //update in the data
            patientObject.addAllUnique("arrayToDos",mTasks);
            patientObject.saveInBackground();



            input.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter task", Toast.LENGTH_LONG).show();
        }
    }

    //ITERATION TO FIND THE OBJECTID AND UPDATE THE LISTVIEW
    private void findObjectId(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getString("patientId"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            patientObject = object;
                            List<Object> item = object.getList("arrayToDos");
                            if (item == null) {
                            }
                            else {
                                for (int i = 0; i < item.size(); i++) {
                                    mTasks.add(item.get(i).toString());
                                    tasksAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    // ADDS A TASK TO THE TASKLIST
    private void addTask(View v) {
        EditText input = findViewById(R.id.inputTaskEditText);
        String taskText = input.getText().toString();

        if(!(taskText.equals(""))) {
            tasksAdapter.add(taskText);
            input.setText("");
            //update in the data
            patientObject.addAllUnique("arrayToDos",mTasks);
            patientObject.saveInBackground();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter task", Toast.LENGTH_LONG).show();
        }
    }

    // DELETES THE TASK WITH A LONG PRESS AFTER DELETE BUTTON IS IMPLEMENTED
    // CHANGE TO SHORT PRESS
    private void deleteTask(View v) {
        setUpTaskViewListener();
    }

    // MOVES THE TASK TO THE EDITTEXT TO BE EDITED AND DELETES THE OLD TASK
    private void taskEditListener() {
        mTaskView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Editing Task", Toast.LENGTH_LONG).show();
                EditText input = findViewById(R.id.inputTaskEditText);
                input.setText(mTasks.get(position));
                //UPDATE IN THE ARRAYLIST
                mTasks.remove(position);
                tasksAdapter.notifyDataSetChanged();
                //UPDATE IN THE DATABSE
                patientObject.put("arrayToDos", mTasks);
                patientObject.saveInBackground();
                return true;
            }
        });
    }

    // ALLOWS THE USER TO EDIT TASK
    private void editTask(View v) {
        taskEditListener();
    }

    // DELETES THE TASK IN THE LIST ON A LONG PRESS
    private void setUpTaskViewListener() {
        mTaskView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Task Removed", Toast.LENGTH_LONG).show();
                //UPDATE IN THE ARRAYLIST
                mTasks.remove(position);
                tasksAdapter.notifyDataSetChanged();
                //UPDATE IN THE DATABASE
                patientObject.put("arrayToDos", mTasks);
                patientObject.saveInBackground();
                return true;
            }
        });
    }

    // SHOWS THE TIME DATE DIALOG
    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(MainTask.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(MainTask.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
