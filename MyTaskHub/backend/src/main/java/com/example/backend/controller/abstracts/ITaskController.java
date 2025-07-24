package com.example.backend.controller.abstracts;

import com.example.backend.entities.Task;

import java.util.List;

public interface ITaskController {
    List<Task> getAll();
    Task getById(Integer id);
    Task create(Task task);
    Task update(Integer id,Task task);
    void delete(Integer id);

}
