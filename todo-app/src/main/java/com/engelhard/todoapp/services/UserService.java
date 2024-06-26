package com.engelhard.todoapp.services;

import com.engelhard.todoapp.models.entities.Token;
import com.engelhard.todoapp.models.dtos.UserLoginDTO;
import com.engelhard.todoapp.models.dtos.UserRegisterDTO;
import com.engelhard.todoapp.models.entities.User;
import java.util.UUID;
import java.util.List;

public interface UserService {
    // Register
    String register(UserRegisterDTO data) throws Exception;

    // Login
    String login(UserLoginDTO data) throws Exception;

    // Find user by id (uuid)
    User findById(UUID id);

    // Find user by id (string)
    User findById(String id);
    
    // Find user by email
    User findByEmail(String email);

    // Get all users
    List<User> getAllUsers();

    // Create token
    Token createToken(User user) throws Exception;

    // TODO: Implement verify token
}
