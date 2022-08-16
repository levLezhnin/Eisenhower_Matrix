package com.llezhnin.eisenhowermatrix;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.llezhnin.eisenhowermatrix.category.CategoryDataManager;
import com.llezhnin.eisenhowermatrix.category.Category;
import com.llezhnin.eisenhowermatrix.database.DatabaseHelper;
import com.llezhnin.eisenhowermatrix.database.DatabaseManager;
import com.llezhnin.eisenhowermatrix.fragments.NewTaskFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    @SuppressLint("StaticFieldLeak")
    public static DatabaseManager databaseManager;
    private static List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        databaseManager = new DatabaseManager(this);

        Button button = findViewById(R.id.btn_add_work);

        button.setOnClickListener(view -> {
            NewTaskFragment newTaskFragment = new NewTaskFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_main, newTaskFragment)
                    .commit();
        });

        categories = new ArrayList<>();
        categories.add(new Category(findViewById(R.id.rv_importance3), 3));
        categories.add(new Category(findViewById(R.id.rv_importance2), 2));
        categories.add(new Category(findViewById(R.id.rv_importance1), 1));
        categories.add(new Category(findViewById(R.id.rv_importance0), 0));

        CategoryDataManager.setCategories(categories);

        databaseManager.onDataChanged();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_main);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Предупреждение")
                    .setMessage("Вы точно хотите выйти из приложения?")
                    .setPositiveButton("Да", (dialog, id) -> finish())
                    .setNegativeButton("Нет", (dialog, id) -> dialog.cancel());
            builder.create();
            builder.show();
        }
    }
}