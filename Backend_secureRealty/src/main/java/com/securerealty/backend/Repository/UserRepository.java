package com.securerealty.backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.securerealty.backend.Model.User;

public interface UserRepository
        extends MongoRepository<User, String> {
	User findByEmail(String email);
	User findByName(String name);

}