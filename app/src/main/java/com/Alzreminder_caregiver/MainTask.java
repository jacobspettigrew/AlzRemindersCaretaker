package com.Alzreminder_caregiver;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



import com.parse.FindCallback;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import java.util.List;

public class MainTask extends AppCompatActivity {

    //var

    private ArrayAdapter<String> itemsAdapter;
    ParseObject patientObject;

    //ui
    private ListView listView;
    private ArrayList<String> tasks;
    private ArrayAdapter<String> tasksAdapter;
    private ListView taskView;
    private Button addButton;
    private Button deleteButton;
    private Button editButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carevier_tasks);

        // Find the UI elements by their id
        taskView = findViewById(R.id.taskView);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        tasks = new ArrayList<>();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);


        findObjectId();
        taskView.setAdapter(tasksAdapter);
        setUpTaskViewListener();

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
    }

    // Watches for a long press that will remove an task from the task list
    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Task Removed", Toast.LENGTH_LONG).show();

                tasks.remove(position);
                itemsAdapter.notifyDataSetChanged();
                //upadate in the data
                patientObject.put("arrayToDos", tasks);
                patientObject.saveInBackground();
                return true;
            }
        });
    }

    // When called, it will add the task to the list
    private void addItem(View view) {
        EditText input = findViewById(R.id.editTextTextPersonName);
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))) {
            tasksAdapter.add(itemText);

            //update in the data
            patientObject.addAllUnique("arrayToDos",tasks);
            patientObject.saveInBackground();
            input.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter task", Toast.LENGTH_LONG).show();
        }
    }

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
                                    tasks.add(item.get(i).toString());
                                    tasksAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
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
            //update in the data
            patientObject.addAllUnique("arrayToDos",tasks);
            patientObject.saveInBackground();
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
                patientObject.put("arrayToDos", tasks);
                patientObject.saveInBackground();
                return true;
            }
        });
    }

    // Allows the user to edit task
    private void editTask(View v) {
        taskEditListener();
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
                //upadate in the data
                patientObject.put("arrayToDos", tasks);
                patientObject.saveInBackground();
                return true;
            }
        });
    }
}
