package com.Alzreminder_caregiver.adapters;

/*

HW 4

Course: CMPT 385 Software Engineering
Instructor: Dr. Herbert H. Tsang
Description: <
     Recyclerview adapter that holds the u.i. and displays
    >
Due date: < 2020/12/02 >
FILE NAME:TaskListAdapter.java
TEAM NAME: Alzreminders
Author: < Kyung Cheol Koh >
Input: < None>
Output: < Initialize the database  >
I pledge that I have completed the programming assignment independently.
I have not copied the code from a student or any source.
I have not given my code to any student.

Sign here: __Kyung Cheol Koh______
*/



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Alzreminder_caregiver.R;
import com.Alzreminder_caregiver.models.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {
    private final LayoutInflater mInflater;
    private List<Task> mTasks;

    public TaskListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (mTasks != null) {
            Task current = mTasks.get(position);
            holder.TaskItemView.setText(current.getTask());
        } else {

            holder.TaskItemView.setText("No Task");
        }
    }

    public void setTasks(List<Task> Tasks){
        mTasks = Tasks;
        notifyDataSetChanged();
    }


    //
    @Override
    public int getItemCount() {
        if (mTasks != null)
            return mTasks.size();
        else return 0;
    }

    public Task getTaskAtPosition (int position) {
        return mTasks.get(position);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView TaskItemView;

        private TaskViewHolder(View itemView) {
            super(itemView);
            TaskItemView = itemView.findViewById(R.id.textView);
        }
    }
}

