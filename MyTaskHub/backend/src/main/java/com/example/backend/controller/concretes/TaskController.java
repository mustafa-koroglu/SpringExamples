package com.example.backend.controller.concretes;

import com.example.backend.business.abstracts.ITaskService;
import com.example.backend.controller.abstracts.ITaskController;
import com.example.backend.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class TaskController implements ITaskController {

    @Autowired
    private ITaskService taskService;

    @GetMapping(path = "/tasks")
    @Override
    public List<Task> getAll() {
        return taskService.getAAll();
    }

    @GetMapping(path = "/tasks/{id}")
    @Override
    public Task getById(@PathVariable Integer id) {

        return taskService.getById(id);
    }

    @PostMapping(path = "/create")
    @Override
    public Task create(@RequestBody Task task) {
        return taskService.save(task);
    }

    @PutMapping(path = "/update")
    @Override
    public Task update(@PathVariable Integer id, @RequestBody Task task) {
        return taskService.update(id, task);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void delete(@PathVariable Integer id) {
        taskService.delete(id);

    }
}
