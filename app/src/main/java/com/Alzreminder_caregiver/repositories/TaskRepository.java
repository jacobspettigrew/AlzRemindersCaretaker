package com.Alzreminder_caregiver.repositories;


import android.app.Application;
import android.content.SharedPreferences;
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

import static com.parse.Parse.getApplicationContext;

public class TaskRepository {
    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;
    private List<Task> mlistTasks;


    public TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.TaskDao();
        getTaskFromDatabase();
        mAllTasks = mTaskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void insert (Task Task) {
        new insertAsyncTask(mTaskDao).execute(Task);
    }

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

    // Need to run off main thread
    public void deleteTask(Task Task) {
        new deleteTaskAsyncTask(mTaskDao).execute(Task);
    }

    public void deleteTaskToDatabse(Task task,int position){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");

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

    /**
     * Delete all Tasks from the database
     */
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

    /**
     *  Delete a single Task from the database.
     */
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

