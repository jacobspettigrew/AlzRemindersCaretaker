/*

HW 4

Course: CMPT 385 Software Engineering
Instructor: Dr. Herbert H. Tsang
Description: <
     The repository to control database
     The repository serves as a path to the local and cloud database.
    >
Due date: < 2020/12/02 >
FILE NAME:TaskRepository.java
TEAM NAME: Alzreminders
Author: < Kyung Cheol Koh >
Input: < None>
Output: < Initialize the database  >
I pledge that I have completed the programming assignment independently.
I have not copied the code from a student or any source.
I have not given my code to any student.

Sign here: __Kyung Cheol Koh______
*/


package com.Alzreminder_caregiver.repositories;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.Alzreminder_caregiver.models.Task;
import com.Alzreminder_caregiver.persistence.TaskDao;
import com.Alzreminder_caregiver.persistence.TaskRoomDatabase;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class TaskRepository {
    //LiveData and Dao
    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;
    private List<Task> mlistTasks;

//Constructor to pass TaskDao and LiveData
    public TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.TaskDao();
        getTaskFromDatabase();
        mAllTasks = mTaskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    //Insert method
    public void insert (Task Task) {
        new insertAsyncTask(mTaskDao).execute(Task);
    }

    //Run in the Background to insert a task to the database
    public void insertToDatabase(Task task) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");

        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getString("patientId"));
        query.findInBackground(
                new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                for (ParseObject object : objects) {
                                    object.add("arrayToDos", task.getTask());
                                    object.saveInBackground();
                                }
                            }
                        }
                    }
                }
        );
    }

    public void deleteAll() {
        new deleteAllTasksAsyncTask(mTaskDao).execute();
    }

    // Run in the Background to Delete a task to the cloud database
    public void deleteTask(Task Task) {
        new deleteTaskAsyncTask(mTaskDao).execute(Task);
    }

    public void deleteTaskToDatabse(Task task,int position){
        //Get Patient column from the clound service
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");
        //Get the patientId and search the  objectId in the Patient class
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getString("patientId"));
        query.findInBackground(
                new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                for (ParseObject object : objects) {
                                    //convert that to list
                                    //delete the particluar item
                                    //put all of the converted list
                                    List<Object> item = object.getList("arrayToDos");
                                    if (item == null) {
                                    }
                                    else {
                                        item.remove(task.getTask());
                                        object.put("arrayToDos",item);
                                        object.saveInBackground();
                                    }

                                }
                            }
                        }
                    }
                }
        );
    }

    public void getTaskFromDatabase(){
        deleteAll();
        // Run in the Background to get tasks from the cloud database
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getString("patientId"));
        //This query looks for all the arrays of tasks from the Data and insert it to the local database
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
                                for (int i = 0; i < item.size(); i++) {
                                    Task task = new Task(item.get(i).toString());
                                    insert(task);
                                    Log.d("Room", "done: " + item.get(i).toString());
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    //Async class which runs in the database to insert a task
    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;
        insertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //Delete all the tasks from the background
    private static class deleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deleteAllTasksAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

   //Delete a single task from the background
    private static class deleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deleteTaskAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.deleteTask(params[0]);
            return null;
        }
    }
}

