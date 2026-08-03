package com.securerealty.backend.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.securerealty.backend.Model.User;
import com.securerealty.backend.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.registerUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return service.getUserById(id);
    }

}