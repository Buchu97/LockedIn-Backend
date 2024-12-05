package com.example.lockedIn.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.lockedIn.model.User;
import com.example.lockedIn.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/")
    public List<User> getAllUsesr() {
        return userService.findAll();
    }

    @GetMapping("/email/{email}")
    public Optional<User> getAllUseByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/register")
    public ResponseEntity<User> postMethodName(@RequestBody User user) {
        User savedUser = userService.registUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

}
