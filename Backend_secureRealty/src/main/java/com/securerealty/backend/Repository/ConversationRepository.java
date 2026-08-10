package com.securerealty.backend.Repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.securerealty.backend.Model.Conversation;

public interface ConversationRepository
        extends MongoRepository<Conversation, String> {
		Conversation findByCustomerIdAndRealtorId(
	        String customerId,
	        String realtorId
	);
		List<Conversation> findByCustomerId(String customerId);

		List<Conversation> findByRealtorId(String realtorId);
}