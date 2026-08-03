package com.securerealty.backend.Service;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.securerealty.backend.Model.*;
import com.securerealty.backend.Repository.*;

@Service

public class ConversationService {
	private final ConversationRepository repository;

    public ConversationService(ConversationRepository repository) {
        this.repository = repository;
    }
    public Conversation createConversation(Conversation conversation) {
    	return repository.save(conversation);
    }
    public Conversation getConversationById(String id) {
        return repository.findById(id).orElse(null);
    }
    public List<Conversation> getAllConversations() {
        return repository.findAll();
    }
    public void deleteConversation(String id) {
        repository.deleteById(id);
    }
    public Conversation getOrCreateConversation(
            String customerId,
            String realtorId) {

        Conversation conversation =
                repository.findByCustomerIdAndRealtorId(
                        customerId,
                        realtorId);

        if (conversation != null) {
            return conversation;
        }

        Conversation newConversation =
                new Conversation();

        newConversation.setCustomerId(customerId);
        newConversation.setRealtorId(realtorId);
        newConversation.setCreatedAt(LocalDateTime.now());

        return repository.save(newConversation);
    }
}
