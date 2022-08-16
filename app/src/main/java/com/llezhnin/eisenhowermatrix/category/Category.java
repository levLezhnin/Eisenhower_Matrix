package com.llezhnin.eisenhowermatrix.category;

import androidx.recyclerview.widget.RecyclerView;

import com.llezhnin.eisenhowermatrix.MainActivity;
import com.llezhnin.eisenhowermatrix.adapter.TaskAdapter;
import com.llezhnin.eisenhowermatrix.domain.Task;
import com.llezhnin.eisenhowermatrix.rest.TaskAPIImplementation;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private final RecyclerView recyclerView;
    private List<Task> tasks;
    private TaskAdapter taskAdapter;
    private final int priority;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Category(RecyclerView recyclerView, List<Task> works_in_category, int priority) {
        this.recyclerView = recyclerView;
        this.tasks = works_in_category;
        this.priority = priority;
        this.taskAdapter = new TaskAdapter(this.recyclerView.getContext(), works_in_category);
        notifyDatasetChanged();
        this.recyclerView.setAdapter(taskAdapter);
    }

    public Category(RecyclerView recyclerView, int priority) {
        this.recyclerView = recyclerView;
        this.tasks = new ArrayList<>();
        this.taskAdapter = new TaskAdapter(getRecyclerView().getContext(), getTasks());
        this.taskAdapter.setPriority(priority);
        this.recyclerView.setAdapter(taskAdapter);
        this.priority = priority;
    }

    public void updateDataset() {
        tasks.clear();
        for (Task w : new TaskAPIImplementation(MainActivity.databaseManager).getAll()) {
            if (w.getPriority() == priority) {
                tasks.add(w);
            }
        }
        notifyDatasetChanged();
    }

    private void notifyDatasetChanged() {
        taskAdapter.notifyDataSetChanged();
    }
}
