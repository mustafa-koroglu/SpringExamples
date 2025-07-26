package com.example.backend.business.concretes;

import com.example.backend.business.abstracts.ITaskService;
import com.example.backend.dto.requests.CreateTaskRequest;
import com.example.backend.dto.requests.UpdateTaskRequest;
import com.example.backend.dto.responses.GetAllTaskResponse;
import com.example.backend.dto.responses.GetByIdTaskReponse;
import com.example.backend.entities.Task;
import com.example.backend.repository.ITaskRepository;
import com.example.backend.utilities.mappers.IModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskManager implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private IModelMapperService modelMapper;


    @Override
    public List<GetAllTaskResponse> getAAll() {
        List<Task> getAll = taskRepository.findAll();
        List<GetAllTaskResponse> getAllTaskResponse = getAll.stream().
                map(task -> this.modelMapper.forResponse().map(task, GetAllTaskResponse.class)).collect(Collectors.toList());
        return getAllTaskResponse;
    }

    @Override
    public GetByIdTaskReponse getById(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow();
        GetByIdTaskReponse getByIdTaskReponse = this.modelMapper.forResponse().map(task, GetByIdTaskReponse.class);
        return getByIdTaskReponse;
    }

    @Override
    public CreateTaskRequest create(CreateTaskRequest createTaskRequest) {
        Task task = this.modelMapper.forRequest().map(createTaskRequest, Task.class);
        taskRepository.save(task);

        return createTaskRequest;
    }

    @Override
    public void delete(Integer id) {
        taskRepository.deleteById(id);

    }

    @Override
    public UpdateTaskRequest update(Integer id, UpdateTaskRequest updateTaskRequest) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setDescription(updateTaskRequest.getDescription());
        task.setTitle(updateTaskRequest.getTitle());
        task.setDueDate(updateTaskRequest.getDueDate());
        task.setStatus(updateTaskRequest.isStatus());
        taskRepository.save(task);
        UpdateTaskRequest updateTask = this.modelMapper.forRequest().map(task, UpdateTaskRequest.class);
        return updateTask;
    }
}
