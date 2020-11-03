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

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTask extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_jacob);

        // Find the UI elements by their id
        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.button);

        // Adds a task to the task list when the add button is clicked
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        items = new ArrayList<>();
        List<Object> item =  ParseUser.getCurrentUser().getList("arrayToDos");
        if(item == null){

        }
        else{
            for (int i = 0; i < item.size(); i++) {
                items.add(item.get(i).toString());
            }
        }



        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
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

                ParseUser.getCurrentUser().put("arrayToDos", items);
                ParseUser.getCurrentUser().saveInBackground();
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

            ParseUser.getCurrentUser().addAllUnique("arrayToDos", items);
            ParseUser.getCurrentUser().saveInBackground();
            input.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter task", Toast.LENGTH_LONG).show();
        }
    }
}
