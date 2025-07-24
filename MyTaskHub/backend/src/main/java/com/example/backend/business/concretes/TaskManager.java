package com.example.backend.business.concretes;

import com.example.backend.business.abstracts.ITaskService;
import com.example.backend.entities.Task;
import com.example.backend.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManager implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;


    @Override
    public List<Task> getAAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

    @Override
    public Task getById(Integer id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.get();
    }

    @Override
    public Task save(Task task) {
        Task newTask = taskRepository.save(task);
        return newTask;
    }

    @Override
    public Task update(Integer id, Task task) {
        Task updatedTask = getById(id);
        updatedTask.setTitle(task.getTitle());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setDueDate(task.getDueDate());
        updatedTask.setStatus(task.isStatus());
        return updatedTask;
    }

    @Override
    public void delete(Integer id) {
        Task task = getById(id);
        taskRepository.delete(task);

    }

}
