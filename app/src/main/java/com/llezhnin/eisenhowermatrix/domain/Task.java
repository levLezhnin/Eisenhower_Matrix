package com.llezhnin.eisenhowermatrix.domain;

import androidx.annotation.NonNull;

import com.llezhnin.eisenhowermatrix.database.DatabaseConstants;
import com.llezhnin.eisenhowermatrix.database.TableWorkConstants;

import java.io.Serializable;

public class Task implements Serializable {

    private static final String TABLE_NAME = TableWorkConstants.TABLE_NAME;

    public static String getTable_Name() {
        return TABLE_NAME;
    }

    private int id;

    private String description;

    private int priority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Task() {
    }

    public Task(int id, String description, int importance) {
        this.id = id;
        this.description = description;
        this.priority = importance;
    }

    public Task(String description, int importance) {
        this.description = description;
        this.priority = importance;
    }



    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}
