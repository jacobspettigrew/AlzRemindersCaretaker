package com.Alzreminder_caregiver.adapters;


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
    private List<Task> mTasks; // Cached copy of Tasks

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
            // Covers the case of data not being ready yet.
            holder.TaskItemView.setText("No Task");
        }
    }

    public void setTasks(List<Task> Tasks){
        mTasks = Tasks;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mTasks has not been updated (means initially, it's null, and we can't return null).
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

