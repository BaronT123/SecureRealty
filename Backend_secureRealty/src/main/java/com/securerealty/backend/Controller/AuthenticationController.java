package com.securerealty.backend.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securerealty.backend.Model.User;
import com.securerealty.backend.Service.AuthService;
import com.securerealty.backend.dto.LoginRequest;
import com.securerealty.backend.dto.LoginResponse;

@RestController
@RequestMapping("/login")

public class AuthenticationController {
	private final AuthService service;

    public AuthenticationController(AuthService service) {
        this.service = service;
    }

    @PostMapping
    public LoginResponse verify(@RequestBody LoginRequest request) {

        String token = service.authenticateUser(
                request.getEmail(),
                request.getPassword());

        return new LoginResponse(token);
    }
}
