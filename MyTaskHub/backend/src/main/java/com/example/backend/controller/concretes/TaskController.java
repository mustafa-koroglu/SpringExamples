package com.example.backend.controller.concretes;

import com.example.backend.business.abstracts.ITaskService;
import com.example.backend.controller.abstracts.ITaskController;
import com.example.backend.dto.requests.CreateTaskRequest;
import com.example.backend.dto.requests.UpdateTaskRequest;
import com.example.backend.dto.responses.GetAllTaskResponse;
import com.example.backend.dto.responses.GetByIdTaskReponse;
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
    public List<GetAllTaskResponse> getAll() {
        return taskService.getAAll();
    }

    @GetMapping(path = "/tasks/{id}")
    @Override
    public GetByIdTaskReponse getById(@PathVariable Integer id) {

        return taskService.getById(id);
    }

    @PostMapping(path = "/create")
    @Override
    public CreateTaskRequest create(@RequestBody CreateTaskRequest createTaskRequest) {
        return taskService.create(createTaskRequest);
    }

    @PutMapping(path = "/update")
    @Override
    public UpdateTaskRequest update(@PathVariable Integer id, @RequestBody UpdateTaskRequest updateTaskRequest) {
        return taskService.update(id, updateTaskRequest);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void delete(@PathVariable Integer id) {
        taskService.delete(id);

    }
}
