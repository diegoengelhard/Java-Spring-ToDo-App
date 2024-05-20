package com.engelhard.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engelhard.todoapp.services.TaskService;

import java.util.List;
import java.util.UUID;

import com.engelhard.todoapp.models.dtos.CreateTaskDTO;
import com.engelhard.todoapp.models.entities.Task;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/task")
@CrossOrigin("*")
public class TaskController {
    // Define task service
    @Autowired
    private TaskService taskService;

    // Get all tasks
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        try {
            // Get all tasks
            List<Task> tasks = taskService.getAllTasks();

            // Return tasks
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get task by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable("id") UUID id){
        try {
            // Get task by id
            Task task = taskService.getTaskById(id);

            // Return task
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get task by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTaskByUserId(@PathVariable("userId") UUID userId){
        try {
            // Get task by user id
            Task task = taskService.getTaskByUserId(userId);

            // Return task
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Create task
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@ModelAttribute @Valid CreateTaskDTO data) {
        try {
            // Create task
            String response = taskService.createTask(data);

            if (!response.isEmpty()) {
                // Return response
                return new ResponseEntity<>("Task created successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
