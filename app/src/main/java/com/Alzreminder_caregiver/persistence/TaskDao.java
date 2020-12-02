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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);
    @Query("DELETE FROM task_table")
    void deleteAll();
    @Delete
    void deleteTask(Task task);

    @Query("SELECT * from task_table ORDER BY task ASC")
    LiveData<List<Task>> getAllTasks();
}
