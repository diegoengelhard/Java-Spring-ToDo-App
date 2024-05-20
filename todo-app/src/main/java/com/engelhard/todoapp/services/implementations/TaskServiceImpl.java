package com.engelhard.todoapp.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engelhard.todoapp.models.dtos.CreateTaskDTO;
import com.engelhard.todoapp.models.entities.Task;
import com.engelhard.todoapp.repositories.TaskRepository;
import com.engelhard.todoapp.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
    // Define repository
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public String createTask(CreateTaskDTO data) throws Exception {
        // Create new task
        Task task = new Task();

        // Set task data
        task.setUserId(data.getUserId());
        task.setTitle(data.getTitle());
        task.setDescription(data.getDescription());

        // Save task
        taskRepository.save(task);

        return "Task created successfully";
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(UUID id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task getTaskByUserId(UUID userId) throws Exception {
        try {
            if (taskRepository.findByUserId(userId).isPresent()) {
                return taskRepository.findByUserId(userId).get();
            } else {
                throw new Exception("Task not found");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception("Task not found");
        }
    }
    
}
