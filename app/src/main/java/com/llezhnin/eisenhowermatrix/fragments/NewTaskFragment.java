package com.llezhnin.eisenhowermatrix.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.llezhnin.eisenhowermatrix.MainActivity;
import com.llezhnin.eisenhowermatrix.R;
import com.llezhnin.eisenhowermatrix.domain.Task;
import com.llezhnin.eisenhowermatrix.rest.TaskAPIImplementation;

public class NewTaskFragment extends Fragment {

    private EditText et_description;
    private CheckBox cb_is_important, cb_is_urgent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_description = view.findViewById(R.id.et_description);
        cb_is_important = view.findViewById(R.id.cb_is_important);
        cb_is_urgent = view.findViewById(R.id.cb_is_urgent);
        Button delete = view.findViewById(R.id.delete);
        delete.setVisibility(View.INVISIBLE);
        Button btn_add = view.findViewById(R.id.apply);

        btn_add.setOnClickListener(view1 -> {
            if(et_description.getText().length() == 0) {
                Toast.makeText(getContext(), "Add a description to your task!", Toast.LENGTH_LONG).show();
            } else {
                int priority = (cb_is_important.isChecked() ? 1 : 0) * 2 + (cb_is_urgent.isChecked() ? 1 : 0);
                Task task = new Task(et_description.getText().toString(), priority);
                new TaskAPIImplementation(MainActivity.databaseManager).insert(task);
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(this)
                        .commit();
                MainActivity.onFragmentFinished();
            }
        });
    }
}