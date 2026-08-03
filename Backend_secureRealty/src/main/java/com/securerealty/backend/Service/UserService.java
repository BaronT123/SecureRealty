package com.securerealty.backend.Service;
import com.securerealty.backend.Repository.UserRepository;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.securerealty.backend.Model.*;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    public User registerUser(User user) {
    	user.setPassword(
    	        passwordEncoder.encode(user.getPassword())
    	    );
        return repository.save(user);
    }
    public User getUserById(String id) {
        return repository.findById(id).orElse(null);
    }
    public User getUserByEmail(String email) {

        return repository.findByEmail(email);

    }
    public List<User> getAllUsers() {
        return repository.findAll();
    }
    public void deleteUser(String id) {
        repository.deleteById(id);
    }

}