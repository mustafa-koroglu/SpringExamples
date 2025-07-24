package com.example.backend.business.abstracts;

import com.example.backend.entities.Task;

import java.util.List;

public interface ITaskService {

    List<Task> getAAll();
    Task getById(Integer id);
    Task save(Task task);
    void delete(Integer id);
    Task update( Integer id,Task task);
}
