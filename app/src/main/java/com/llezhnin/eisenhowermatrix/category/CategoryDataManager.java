package com.llezhnin.eisenhowermatrix.category;

import com.llezhnin.eisenhowermatrix.domain.Task;

import java.util.List;

public class CategoryDataManager {

    public static Task buffered_task; //buffers a task, which is being dragged by a user

    private static List<CustomRecyclerView> categories;

    public CategoryDataManager(List<CustomRecyclerView> categories) {
        CategoryDataManager.categories = categories;
    }

    public static void setCategories(List<CustomRecyclerView> categories) {
        CategoryDataManager.categories = categories;
    }

    public static void updateAllDatasets() {
        for (CustomRecyclerView c : categories) {
            c.updateDataset();
        }
    }
}
