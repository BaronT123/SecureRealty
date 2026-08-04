package com.securerealty.backend.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.securerealty.backend.Model.User;
import com.securerealty.backend.Repository.UserRepository;
import com.securerealty.backend.dto.LoginResponse;

@Service
public class AuthService {
	private final UserRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	public AuthService(UserRepository repository,BCryptPasswordEncoder passwordEncoder,JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
	public LoginResponse authenticateUser(String email, String password) {

	    User user = repository.findByEmail(email);

	    if (user == null) {
	        return null;
	    }

	    if (passwordEncoder.matches(password, user.getPassword())) {

	        String token = jwtService.generateToken(user);
	        System.out.println("===== LOGIN SUCCESS =====");
	        System.out.println("User: " + user.getEmail());
	        System.out.println("Role: " + user.getRole());
	        System.out.println("JWT: " + token);

	        return new LoginResponse(
	                token,
	                user.getRole()
	        );
	    }

	    return null;
	}
}
