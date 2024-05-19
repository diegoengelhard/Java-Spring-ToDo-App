package com.engelhard.todoapp.repositories;

import com.engelhard.todoapp.models.entities.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID>{
    // Find user by username or email
    User findByUsernameOrEmail(String username, String email);
}
