package com.llezhnin.eisenhowermatrix;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.llezhnin.eisenhowermatrix.category.CategoryDataManager;
import com.llezhnin.eisenhowermatrix.category.CustomRecyclerView;
import com.llezhnin.eisenhowermatrix.database.DatabaseHelper;
import com.llezhnin.eisenhowermatrix.database.DatabaseManager;
import com.llezhnin.eisenhowermatrix.fragments.NewTaskFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    @SuppressLint("StaticFieldLeak")
    public static DatabaseManager databaseManager;
    @SuppressLint("StaticFieldLeak")
    private static Button btn_add_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        databaseManager = new DatabaseManager(this);

        btn_add_task = findViewById(R.id.btn_add_task);

        btn_add_task.setOnClickListener(view -> {
            NewTaskFragment newTaskFragment = new NewTaskFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.fl_main, newTaskFragment)
                    .commit();
            onFragmentStarted();
        });

        List<CustomRecyclerView> categories = new ArrayList<>();
        categories.add(new CustomRecyclerView(findViewById(R.id.rv_importance3), 3));
        categories.add(new CustomRecyclerView(findViewById(R.id.rv_importance2), 2));
        categories.add(new CustomRecyclerView(findViewById(R.id.rv_importance1), 1));
        categories.add(new CustomRecyclerView(findViewById(R.id.rv_importance0), 0));

        CategoryDataManager.setCategories(categories);

        databaseManager.onDataChanged();
    }

    public static void onFragmentStarted() {
        btn_add_task.setVisibility(View.INVISIBLE);
    }

    public static void onFragmentFinished() {
        btn_add_task.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_main);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .remove(fragment)
                    .commit();
            onFragmentFinished();
        } else {
            Resources resources = getResources();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(resources.getString(R.string.alert))
                    .setMessage(resources.getString(R.string.alert_message))
                    .setPositiveButton(resources.getString(R.string.yes), (dialog, id) -> finish())
                    .setNegativeButton(resources.getString(R.string.no), (dialog, id) -> dialog.cancel());
            builder.create();
            builder.show();
        }
    }
}