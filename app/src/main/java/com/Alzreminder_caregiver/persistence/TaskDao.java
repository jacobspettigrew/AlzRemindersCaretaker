/*

HW 4

Course: CMPT 385 Software Engineering
Instructor: Dr. Herbert H. Tsang
Description: <
     Actual queries to the database: add, delete and get Livedata
    >
Due date: < 2020/12/02 >
FILE NAME:TaskDao.java
TEAM NAME: Alzreminders
Author: < Kyung Cheol Koh >
Input: < None>
Output: < Initialize the database  >
I pledge that I have completed the programming assignment independently.
I have not copied the code from a student or any source.
I have not given my code to any student.

Sign here: __Kyung Cheol Koh______
*/


package com.Alzreminder_caregiver.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.Alzreminder_caregiver.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    //Add a task
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);
    @Query("DELETE FROM task_table")
    void deleteAll();
    @Delete
    void deleteTask(Task task);

    //Get all the tasks
    @Query("SELECT * from task_table ORDER BY task ASC")
    LiveData<List<Task>> getAllTasks();
}
