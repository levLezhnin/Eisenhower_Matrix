package com.llezhnin.eisenhowermatrix.rest;

import android.content.ContentValues;
import android.database.Cursor;

import com.llezhnin.eisenhowermatrix.database.DatabaseManager;
import com.llezhnin.eisenhowermatrix.database.TableWorkConstants;
import com.llezhnin.eisenhowermatrix.domain.Task;
import com.llezhnin.eisenhowermatrix.domain.mapper.TaskMapper;

import java.util.ArrayList;
import java.util.List;

public class TaskAPIImplementation implements TaskAPI {

    private DatabaseManager databaseManager;

    public TaskAPIImplementation(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void insert(Task task) {
        databaseManager.insertToDb(Task.getTable_Name(), TaskMapper.toContentValues(task));
    }

    @Override
    public void insert(String description, int importance) {
        Task task = new Task(description, importance);
        insert(task);
    }

    @Override
    public Task getById(int id) {
        ContentValues contentValues = databaseManager.getById(Task.getTable_Name(), id);
        return TaskMapper.contentValuesToWork(contentValues);
    }

    @Override
    public List<Task> getAll() {
        List<Task> task_list = new ArrayList<>();
        Cursor cursor = databaseManager.readAllData(Task.getTable_Name());
        while(cursor.moveToNext()) {
            task_list.add(new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
        }
        return task_list;
    }

    @Override
    public Task getByDescription(String description) {
        Cursor cursor = databaseManager.readAllData(Task.getTable_Name());
        while(cursor.moveToNext()) {
            if(cursor.getString(1).equals(description)) {
                return new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            }
        }
        return null;
    }

    @Override
    public void update(int id, Task task) {
        databaseManager.updateInsideDB(Task.getTable_Name(), id, TaskMapper.toContentValues(task));
    }

    @Override
    public void update(int id, String description, int importance) {
        Task task = new Task(description, importance);
        update(id, task);
    }

    @Override
    public void delete(int id) {
        databaseManager.deleteById(Task.getTable_Name(), id);
        rebuildIndexes();
    }

    @Override
    public void rebuildIndexes() {
        List<Task> tasks_buffer = getAll();
        databaseManager.openDB();
        databaseManager.getDb().execSQL(TableWorkConstants.DROP_TABLE);
        databaseManager.getDb().execSQL(TableWorkConstants.TABLE_STRUCTURE);
        int index = 1;
        for(Task t : tasks_buffer) {
            t.setId(index);
            insert(t);
            index++;
        }
        System.out.println(getAll());
    }
}
