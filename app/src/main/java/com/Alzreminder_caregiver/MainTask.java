package com.Alzreminder_caregiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTask extends AppCompatActivity {

    //var
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    ParseObject patientObject;

    //ui
    private ListView listView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_jacob);

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.button);



        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        findObjectId();

        // Adds a task to the task list when the add button is clicked
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    // Watches for a long press that will remove an task from the task list
    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Task Removed", Toast.LENGTH_LONG).show();

                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                //upadate in the data
                patientObject.put("arrayToDos", items);
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
            itemsAdapter.add(itemText);

            //update in the data
            patientObject.addAllUnique("arrayToDos",items);
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
                                    items.add(item.get(i).toString());
                                    itemsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
