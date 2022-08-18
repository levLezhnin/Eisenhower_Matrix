package com.llezhnin.eisenhowermatrix.rest;

import com.llezhnin.eisenhowermatrix.domain.Task;

import java.util.List;

public interface TaskAPI {

    void insert(Task task);

    void insert(String description, int importance);

    Task getByDescription(String description);

    Task getById(int id);

    List<Task> getAll();

    void update(int id, Task task);

    void update(int id, String description, int importance);

    void delete(int id);

    void rebuildIndexes();
}
