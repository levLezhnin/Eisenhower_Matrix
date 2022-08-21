package com.llezhnin.eisenhowermatrix.domain.mapper;

import android.content.ContentValues;

import com.llezhnin.eisenhowermatrix.database.TableWorkConstants;
import com.llezhnin.eisenhowermatrix.domain.Task;

public class TaskMapper {

    public static ContentValues toContentValues(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableWorkConstants.COLUMN_NAME_DESCRIPTION, task.getDescription());
        contentValues.put(TableWorkConstants.COLUMN_NAME_PRIORITY, task.getPriorityInt());
        return contentValues;
    }

    public static Task contentValuesToWork(ContentValues contentValues) {
        Task task = new Task();
        task.setId(Integer.parseInt(String.valueOf(contentValues.get(TableWorkConstants.COLUMN_NAME_ID))));
        task.setDescription((String) contentValues.get(TableWorkConstants.COLUMN_NAME_DESCRIPTION));
        task.setPriority(Integer.parseInt(String.valueOf(contentValues.get(TableWorkConstants.COLUMN_NAME_PRIORITY))));
        return task;
    }
}
