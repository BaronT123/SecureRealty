package com.securerealty.backend.Controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.*;

import com.securerealty.backend.Model.User;
import com.securerealty.backend.Repository.UserRepository;

@RestController
@RequestMapping("/preapproval")
public class PreapprovalController {

    private final UserRepository userRepository;

    public PreapprovalController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String approve(Principal principal) {

        String username = principal.getName();

        User user = userRepository.findByName(username);

        if (user == null) {
            return "User not found";
        }

        if ("CLIENT".equals(user.getRole())) {
            return "Already approved";
        }

        user.setRole("CLIENT");

        userRepository.save(user);

        return "Pre-Approval Successful";
    }
}