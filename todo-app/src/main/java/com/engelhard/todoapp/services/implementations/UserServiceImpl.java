package com.engelhard.todoapp.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.engelhard.todoapp.repositories.UserRepository;
import com.engelhard.todoapp.models.dtos.UserLoginDTO;
import com.engelhard.todoapp.models.dtos.UserRegisterDTO;
import com.engelhard.todoapp.models.entities.Token;
import com.engelhard.todoapp.models.entities.User;
import com.engelhard.todoapp.services.UserService;
import com.engelhard.todoapp.utils.HashPassword;
import com.engelhard.todoapp.utils.JwtTools;

@Service
public class UserServiceImpl implements UserService {
    // Define User Repository
    @Autowired
    private UserRepository userRepository;

    // Define password encoder
    @Autowired
    private HashPassword hashPassword;

    // Define JWT Tools
    @Autowired
    private JwtTools jwtTools;
    
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

    // register user
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

            // Hash password
            String hashedPassword = hashPassword.passwordEncoder().encode(data.getPassword());
            user.setPassword(hashedPassword);

            // Save user
            userRepository.save(user);
            return "Register successful... User created...";
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public String login(UserLoginDTO data) throws Exception {
        try {
            // Find user by email
            User user = userRepository.findByUsernameOrEmail(data.getEmail(), data.getEmail());

            // Check if user exists
            if (user == null) {
                throw new Exception("User not found");
            }

            // Check if password is correct
            if (!hashPassword.passwordEncoder().matches(data.getPassword(), user.getPassword())) {
                throw new Exception("Invalid password");
            }

            return "Login successful...";
        } catch (Exception e) {
            throw e;
        }
    }

    // get all users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // create token
    @Override
    public Token createToken(User user) throws Exception {
        // Generate token
        String tokenString = jwtTools.generateToken(user);

        // Check if token sint empty
        if (tokenString == null) {
            throw new Exception("Token creation failed");
        }

        // Create token
        Token token = new Token(tokenString, user);

        // Return token
        return token;
    }

}
