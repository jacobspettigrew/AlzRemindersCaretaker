/*

HW 4

Course: CMPT 385 Software Engineering
Instructor: Dr. Herbert H. Tsang
Description: <
     The model and entity class: The class includes all the necessary information stored for tasks
    >
Due date: < 2020/12/02 >
FILE NAME:Task.java
TEAM NAME: Alzreminders
Author: < Kyung Cheol Koh >
Input: < None>
Output: < Stores the actual tasks  >
I pledge that I have completed the programming assignment independently.
I have not copied the code from a student or any source.
I have not given my code to any student.

Sign here: __Kyung Cheol Koh______
*/



package com.Alzreminder_caregiver.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    //The primary key used to find and sort
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task")
    private String mTask;

    //Constructor
    public Task(@NonNull String task) {this.mTask = task;}
    public String getTask(){return this.mTask;}
}
