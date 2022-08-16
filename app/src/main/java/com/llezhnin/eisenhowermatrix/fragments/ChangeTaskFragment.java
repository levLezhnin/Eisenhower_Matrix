package com.llezhnin.eisenhowermatrix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.llezhnin.eisenhowermatrix.MainActivity;
import com.llezhnin.eisenhowermatrix.R;
import com.llezhnin.eisenhowermatrix.domain.Task;
import com.llezhnin.eisenhowermatrix.rest.TaskAPIImplementation;

public class ChangeTaskFragment extends Fragment {

    private EditText et_description;
    private CheckBox cb_is_important, cb_is_urgent;
    private Button apply, delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        Task task = (Task) getArguments().getSerializable("Task");

        et_description = view.findViewById(R.id.et_description);
        et_description.setText(task.getDescription());
        cb_is_important = view.findViewById(R.id.cb_is_important);
        cb_is_urgent = view.findViewById(R.id.cb_is_urgent);

        String booleans = Integer.toBinaryString(task.getPriority());
        String[] booleans_separated = booleans.split("");

        if(booleans_separated.length > 1) {
            cb_is_important.setChecked(Integer.parseInt(booleans_separated[0]) >= 1);
            cb_is_urgent.setChecked(Integer.parseInt(booleans_separated[1]) >= 1);
        } else {
            if(Integer.parseInt(booleans_separated[0]) == 1){
                cb_is_important.setChecked(false);
                cb_is_urgent.setChecked(true);
            } else {
                cb_is_important.setChecked(false);
                cb_is_urgent.setChecked(false);
            }
        }

        apply = view.findViewById(R.id.apply);

        apply.setOnClickListener(view1 -> {
            if(et_description.getText().length() == 0) {
                Toast.makeText(getContext(), "Add a description to your task!", Toast.LENGTH_LONG).show();
            } else {
                int priority = (cb_is_important.isChecked() ? 1 : 0) * 2 + (cb_is_urgent.isChecked() ? 1 : 0);
                Task new_task = new Task(et_description.getText().toString(), priority);
                new TaskAPIImplementation(MainActivity.databaseManager).update(task.getId(), new_task);
                getFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
            }
        });

        delete = view.findViewById(R.id.delete);
        delete.setEnabled(true);

        delete.setOnClickListener(view2 -> {
            new TaskAPIImplementation(MainActivity.databaseManager).delete(task.getId());
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();
        });

        return view;
    }
}
