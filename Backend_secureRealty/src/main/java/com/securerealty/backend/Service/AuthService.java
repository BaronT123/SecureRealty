package com.securerealty.backend.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.securerealty.backend.Model.User;
import com.securerealty.backend.Repository.UserRepository;

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
	public String authenticateUser(String email,String password) {

		User user = repository.findByEmail(email);

	    if (user == null) {
	        return null;
	    }

	    if (user != null &&
	    	    passwordEncoder.matches(password, user.getPassword())) {

	    	return jwtService.generateToken(user);
	    	}


	    return null;
}
}
