package com.llezhnin.eisenhowermatrix.category;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.llezhnin.eisenhowermatrix.MainActivity;
import com.llezhnin.eisenhowermatrix.adapter.TaskAdapter;
import com.llezhnin.eisenhowermatrix.domain.Task;
import com.llezhnin.eisenhowermatrix.rest.TaskAPIImplementation;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerView {

    private final RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private final int priority;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public CustomRecyclerView(RecyclerView recyclerView, int priority) {
        this.recyclerView = recyclerView;
        this.priority = priority;
        this.taskAdapter = new TaskAdapter(getRecyclerView().getContext(), new ArrayList<>());
        updateDataset();
        this.recyclerView.setAdapter(taskAdapter);
        this.recyclerView.setLayoutManager(new CustomGridLayoutManager(this.recyclerView.getContext(), 2));
        this.recyclerView.setHasFixedSize(true);
//        this.recyclerView.setOnDragListener((view, dragEvent) -> {
//            if (dragEvent.getAction() == DragEvent.ACTION_DROP) {
//                Task task = CategoryDataManager.buffered_task;
//                task.setPriority(this.priority);
//                new TaskAPIImplementation(MainActivity.databaseManager).update(task.getId(), task);
//                updateDataset();
//                this.taskAdapter.notifyItemRangeInserted(0, taskAdapter.getItemCount());
//            }
//            return true;
//        });
    }

    public void updateDataset() {
        List<Task> tasks_buffer = new ArrayList<>();
        for (Task t : new TaskAPIImplementation(MainActivity.databaseManager).getAll()) {
            if (t.getPriorityInt() == priority) {
                tasks_buffer.add(t);
            }
        }
        taskAdapter.setTaskList(tasks_buffer);
        notifyDatasetChanged();
    }

    private void notifyDatasetChanged() {
        taskAdapter.notifyDataSetChanged();
    }

    private static class CustomGridLayoutManager extends GridLayoutManager {

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public CustomGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public CustomGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public CustomGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }
    }
}
