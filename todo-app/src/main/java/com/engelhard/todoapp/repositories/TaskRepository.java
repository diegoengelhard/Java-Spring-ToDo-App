package com.engelhard.todoapp.repositories;

import com.engelhard.todoapp.models.entities.Task;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, UUID>{
    // Get task by id
    Optional<Task> findById(UUID id);

    // Get task by user id
    Optional<Task> findByUserId(UUID userId);
}
