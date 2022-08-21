package com.llezhnin.eisenhowermatrix.domain;

import androidx.annotation.NonNull;

import com.llezhnin.eisenhowermatrix.R;
import com.llezhnin.eisenhowermatrix.database.TableWorkConstants;

import java.io.Serializable;

public class Task implements Serializable {

    private static final String TABLE_NAME = TableWorkConstants.TABLE_NAME;

    public static String getTable_Name() {
        return TABLE_NAME;
    }

    private int id;

    private String description;

    private Priority priority;

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

    public Priority getPriority() {
        return priority;
    }

    public int getPriorityInt() {
        return priority.ordinal();
    }

    public void setPriority(int priority) {
        if (priority > 3 || priority < 0) {
            try {
                throw new Exception("Incorrect priority value: " + priority);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.priority = Priority.values()[priority];
        }
    }

    public Task() {

    }
    public Task(int id, String description, int importance) {
        this.id = id;
        this.description = description;
        setPriority(importance);
    }

    public Task(String description, int importance) {
        this.description = description;
        setPriority(importance);
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

    public enum Priority {

        NOT_IMPORTANT_NOT_URGENT(R.drawable.style_not_important_not_urgent),
        NOT_IMPORTANT_URGENT(R.drawable.style_not_important_urgent),
        IMPORTANT_NOT_URGENT(R.drawable.style_important_not_urgent),
        IMPORTANT_URGENT(R.drawable.style_important_urgent);


        private final int drawable_id;

        Priority(int color_id) {
            this.drawable_id = color_id;
        }

        public int getDrawable_id() {
            return drawable_id;
        }
    }
}
