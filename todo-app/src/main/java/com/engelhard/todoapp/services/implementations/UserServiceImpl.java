package com.engelhard.todoapp.services.implementations;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.engelhard.todoapp.repositories.UserRepository;
import com.engelhard.todoapp.models.dtos.UserLoginDTO;
import com.engelhard.todoapp.models.dtos.UserRegisterDTO;
import com.engelhard.todoapp.models.entities.User;
import com.engelhard.todoapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    // Define User Repository
    @Autowired
    private UserRepository userRepository;
    
    // find user by id (uuid)
    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    // find user by id (string)
    @Override
    public User findById(String id) {
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    // find user by email
    @Override
    public User findByEmail(String email) {
        return userRepository.findByUsernameOrEmail(email, email);
    }

    @Override
    public String register(UserRegisterDTO data) throws Exception {
        try {
            // Check if user exists
            if (userRepository.findByUsernameOrEmail(data.getFullname(), data.getEmail()) != null) {
                throw new Exception("User already exists");
            }

            // Create new user
            User user = new User();

            // Set user data
            user.setFullname(data.getFullname());
            user.setEmail(data.getEmail());
            user.setPassword(data.getPassword());

            // Save user
            userRepository.save(user);
            return "User created";
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public String login(UserLoginDTO data) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

}
