package com.example.backend.business.abstracts;

import com.example.backend.dto.requests.CreateTaskRequest;
import com.example.backend.dto.requests.UpdateTaskRequest;
import com.example.backend.dto.responses.GetAllTaskResponse;
import com.example.backend.dto.responses.GetByIdTaskReponse;
import com.example.backend.entities.Task;

import java.util.List;

public interface ITaskService {

    List<GetAllTaskResponse> getAAll();

    GetByIdTaskReponse getById(Integer id);

    CreateTaskRequest create(CreateTaskRequest createTaskRequest);

    void delete(Integer id);

    UpdateTaskRequest update(Integer id, UpdateTaskRequest updateTaskRequest);
}
