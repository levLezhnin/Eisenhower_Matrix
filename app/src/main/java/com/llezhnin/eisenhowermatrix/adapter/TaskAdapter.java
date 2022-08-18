package com.llezhnin.eisenhowermatrix.adapter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.llezhnin.eisenhowermatrix.MainActivity;
import com.llezhnin.eisenhowermatrix.R;
import com.llezhnin.eisenhowermatrix.category.CategoryDataManager;
import com.llezhnin.eisenhowermatrix.domain.Task;
import com.llezhnin.eisenhowermatrix.fragments.ChangeTaskFragment;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Task> taskList;

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = new ArrayList<>(taskList);
    }

    public void addTaskIntoList(Task task) {
        taskList.add(task);
    }

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.taskList = taskList;
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView description;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tv_work_description);
        }
    }


    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.task_item, viewGroup, false);

        return new TaskHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Task task = taskList.get(i);

        ((TaskHolder) viewHolder).description.setText(task.getDescription());

        GestureDetector gestureDetector = new GestureDetector(layoutInflater.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                ChangeTaskFragment changeTaskFragment = new ChangeTaskFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Task", task);
                changeTaskFragment.setArguments(bundle);
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_main, changeTaskFragment)
                        .commit();
                return super.onDoubleTap(e);
            }
        });

        viewHolder.itemView.setOnTouchListener((view, motionEvent) -> gestureDetector.onTouchEvent(motionEvent));

//        viewHolder.itemView.setOnLongClickListener(view -> {
//            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//            vibrator.vibrate(100);
//            ClipData clipData = ClipData.newPlainText(String.valueOf(task.getId()), null);
//            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//            view.startDrag(clipData, shadowBuilder, null, 0);
//            return true;
//        });
//
//        viewHolder.itemView.setOnDragListener((v, e) ->
//        {
//            switch (e.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    if (e.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
//                        v.invalidate();
//                        for (Task t : taskList) {
//                            if (t.getId() == Integer.parseInt(e.getClipDescription().getLabel().toString())) {
//                                CategoryDataManager.buffered_task = t;
//                                taskList.remove(t);
//                                break;
//                            }
//                        }
//                        if (CategoryDataManager.buffered_task == null) {
//                            try {
//                                throw new Exception("something went wrong");
//                            } catch (Exception exception) {
//                                exception.printStackTrace();
//                            }
//                        }
//                        return true;
//                    } else {
//                        return false;
//                    }
//
//                case DragEvent.ACTION_DRAG_ENDED:
//
//                    CategoryDataManager.updateAllDatasets();
//
//                    v.invalidate();
//
//                    if (e.getResult()) {
//                        Log.d("DRAGNDROP_DEBUG", "The drop was handled.");
//                    } else {
//                        Log.d("DRAGNDROP_DEBUG", "The drop didn't work.");
//                    }
//
//                    return false;
//
//                default:
//                    Log.e("DragDrop Example", "Unknown action type received by View.OnDragListener.");
//                    break;
//            }
//            return false;
//        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
