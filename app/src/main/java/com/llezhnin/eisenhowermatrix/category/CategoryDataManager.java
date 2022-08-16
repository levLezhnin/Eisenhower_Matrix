package com.llezhnin.eisenhowermatrix.category;

import android.util.Log;

import com.llezhnin.eisenhowermatrix.MainActivity;
import com.llezhnin.eisenhowermatrix.domain.Task;
import com.llezhnin.eisenhowermatrix.rest.TaskAPIImplementation;

import java.util.List;

public class CategoryDataManager {

    private static List<Category> categories;

    public CategoryDataManager(List<Category> categories) {
        CategoryDataManager.categories = categories;
    }

    public static void setCategories(List<Category> categories) {
        CategoryDataManager.categories = categories;
    }

    public static void updateDataset() {
        for (Category c : categories) {
            c.updateDataset();
        }
        Log.d("DATASET_DEBUG", "dataset updated");

        List<Task> existing_tasks = new TaskAPIImplementation(MainActivity.databaseManager).getAll();
        for (Task t : existing_tasks) {
            System.out.println(t.toString());
        }
    }
}
