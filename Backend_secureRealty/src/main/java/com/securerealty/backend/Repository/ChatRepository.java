package com.securerealty.backend.Repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.securerealty.backend.Model.ChatMessage;

public interface ChatRepository
        extends MongoRepository<ChatMessage, String> {
	List<ChatMessage> findByConversationIdOrderByTimestampAsc(
            String conversationId);
}