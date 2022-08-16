package com.llezhnin.eisenhowermatrix.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.llezhnin.eisenhowermatrix.MainActivity;
import com.llezhnin.eisenhowermatrix.R;
import com.llezhnin.eisenhowermatrix.domain.Task;
import com.llezhnin.eisenhowermatrix.fragments.ChangeTaskFragment;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Task> taskList;
    private int priority;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Task> getTaskList() {
        return taskList;
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

        viewHolder.itemView.setOnLongClickListener(view -> {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            ChangeTaskFragment changeTaskFragment = new ChangeTaskFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Task", task);
            changeTaskFragment.setArguments(bundle);
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_main, changeTaskFragment)
                    .commit();
            return true;
        });

//        viewHolder.itemView.setOnLongClickListener(view -> {
//            Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//            vibe.vibrate(100);
//            ClipData data = ClipData.newPlainText("Task", String.valueOf(task.getId()));
//            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//            view.startDrag(data, shadowBuilder, null, 0);
//            return true;
//        });
//
//        viewHolder.itemView.setOnDragListener((v, e) ->
//        {
//            Task buffered_task = null;
//            RecyclerView target;
//            switch (e.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    if (e.getClipDescription().getLabel().equals("Task")) {
//                        v.invalidate();
//                        for (Task t : taskList) {
//                            if(t.getId() == Integer.parseInt(String.valueOf(e.getClipDescription()))) {
//                                buffered_task = t;
//                            }
//                        }
//                        if(buffered_task == null) {
//                            try {
//                                throw new Exception("something went wrong");
//                            } catch (Exception exception) {
//                                exception.printStackTrace();
//                            }
//                        }
//                        return true;
//                    }
//                    return false;
//                case DragEvent.ACTION_DRAG_ENTERED:
//
//                    v.invalidate();
//
//                    return true;
//
//                case DragEvent.ACTION_DRAG_LOCATION:
//
//                    return true;
//
//                case DragEvent.ACTION_DRAG_EXITED:
//
//                    v.invalidate();
//
//                    return true;
//
//                case DragEvent.ACTION_DROP:
//
//
//                    return true;
//
//                case DragEvent.ACTION_DRAG_ENDED:
//
//                    v.invalidate();
//
//                    if (e.getResult()) {
//                        Log.d("DRAGNDROP_DEBUG", "The drop was handled.");
//                    } else {
//                        Log.d("DRAGNDROP_DEBUG", "The drop didn't work.");
//                    }
//
//                    return true;
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
