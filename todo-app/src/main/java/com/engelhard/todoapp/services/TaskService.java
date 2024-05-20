package com.engelhard.todoapp.services;

import java.util.List;
import java.util.UUID;

import com.engelhard.todoapp.models.dtos.CreateTaskDTO;
import com.engelhard.todoapp.models.entities.Task;

public interface TaskService {
    // Create task
    String createTask(CreateTaskDTO data) throws Exception;

    // Get all tasks
    List<Task> getAllTasks();

    // Get task by id
    Task getTaskById(UUID id);
    
    // Get task by user id
    Task getTaskByUserId(UUID userId) throws Exception;
}
