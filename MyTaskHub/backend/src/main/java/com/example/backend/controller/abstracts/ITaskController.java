package com.example.backend.controller.abstracts;

import com.example.backend.dto.requests.CreateTaskRequest;
import com.example.backend.dto.requests.UpdateTaskRequest;
import com.example.backend.dto.responses.GetAllTaskResponse;
import com.example.backend.dto.responses.GetByIdTaskReponse;
import com.example.backend.entities.Task;

import java.util.List;

public interface ITaskController {
    List<GetAllTaskResponse> getAll();

    GetByIdTaskReponse getById(Integer id);

    CreateTaskRequest create(CreateTaskRequest createTaskRequest);

    UpdateTaskRequest update(Integer id, UpdateTaskRequest updateTaskRequest);

    void delete(Integer id);

}
