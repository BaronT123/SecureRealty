package com.securerealty.backend.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.securerealty.backend.Model.Conversation;

public interface ConversationRepository
        extends MongoRepository<Conversation, String> {
		Conversation findByCustomerIdAndRealtorId(
	        String customerId,
	        String realtorId
	);
}