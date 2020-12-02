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


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.Alzreminder_caregiver.adapters.TaskListAdapter;
import com.Alzreminder_caregiver.models.Task;
import com.Alzreminder_caregiver.viewmodels.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainTask extends AppCompatActivity {


    static ArrayList<String> Tasks;
    private SwipeRefreshLayout mSwipeRefresh;

    //DATABASE
    private ParseObject mPatientObject;
    static SharedPreferences mPref;
    SharedPreferences.Editor mEditor;

    // UI
    TextView mPatientidTextView;
    TaskListAdapter adapter;

    public static final int NEW_Task_ACTIVITY_REQUEST_CODE = 1;
    private TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        findObjectId();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedTasks.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> Tasks) {
                // Update the cached copy of the Tasks in the adapter.
                adapter.setTasks(Tasks);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTask.this, NewTaskActivity.class);
                startActivityForResult(intent, NEW_Task_ACTIVITY_REQUEST_CODE);
            }
        });


        //swipe to delete
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Task myTask = adapter.getTaskAtPosition(position);
                        Toast.makeText(MainTask.this, "Deleting " +
                                myTask.getTask(), Toast.LENGTH_LONG).show();

                        // Delete the Task
                        mTaskViewModel.deleteTask(myTask);
                        mTaskViewModel.deeletaTaskToDatabse(myTask,position);
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        //REFRESH LISTENER
        mSwipeRefresh = findViewById(R.id.swiperefreshItem);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mTaskViewModel.getTaskFromDatabse();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefresh.isRefreshing()) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_Task_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Task Task = new Task(data.getStringExtra(NewTaskActivity.EXTRA_REPLY_Tasks));
            mTaskViewModel.insert(Task);
            try {
                mTaskViewModel.insertToDatabase(Task);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


    //ITERATION TO FIND THE TASKS GIVEN THE UNIQUE ID
    private void findObjectId() {
        Tasks = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getString("patientId"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            List<Object> item = object.getList("arrayToDos");
                            if (item == null) {
                                Log.d("Room", "done:  item empty");
                            } else {
                                List<Task> listTasks = new LinkedList<>();
                                for (int i = 0; i < item.size(); i++) {
                                    Task task = new Task(item.get(i).toString());
                                    listTasks.add(task);
                                    Tasks.add(item.get(i).toString());
                                    Log.d("Room", "done: " + item.get(i).toString());
                                }
                                adapter.setTasks(listTasks);
                            }
                        }
                    }
                }
            }
        });
    }
}
