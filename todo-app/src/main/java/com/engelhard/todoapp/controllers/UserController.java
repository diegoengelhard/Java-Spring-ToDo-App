package com.engelhard.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import java.util.List;
import com.engelhard.todoapp.models.dtos.UserLoginDTO;
import com.engelhard.todoapp.models.dtos.UserRegisterDTO;
import com.engelhard.todoapp.models.entities.Token;
import com.engelhard.todoapp.models.entities.User;
import com.engelhard.todoapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    // Define user service
    @Autowired
    private UserService userService;

    // Register user
    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@ModelAttribute @Valid UserRegisterDTO data, BindingResult result) {
        try {
            // Create user
            String response = userService.register(data);

            if (!response.isEmpty()) {
                // Return response
                return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Register user
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@ModelAttribute @Valid UserLoginDTO data, BindingResult result) {
        try {
            // Check for errors
            if (result.hasErrors()) {
                return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
            }

            // Check if user exists
            if (userService.login(data) != null) {
                User user = userService.findByEmail(data.getEmail());

                // Create token & return
                try {
                    Token token = userService.createToken(user);
                    return new ResponseEntity<>(token, HttpStatus.OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Unauthorized", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get all users
    @PostMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();

            if (!users.isEmpty()) { 
                return new ResponseEntity<>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No users found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
