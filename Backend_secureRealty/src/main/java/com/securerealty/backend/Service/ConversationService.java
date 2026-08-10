package com.securerealty.backend.Service;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.securerealty.backend.Model.*;
import com.securerealty.backend.Repository.*;

@Service

public class ConversationService {
	private final ConversationRepository crepository;
	private final UserRepository urepository;

    public ConversationService(ConversationRepository crepository, UserRepository urepository) {
        this.crepository = crepository;
		this.urepository = urepository;
    }
    public Conversation createConversation(Conversation conversation) {
    	return crepository.save(conversation);
    }
    public Conversation getConversationById(String id) {
        return crepository.findById(id).orElse(null);
    }
    public List<Conversation> getConversations(String username) {

        User user = urepository.findByName(username);

        if ("CLIENT".equals(user.getRole())) {

            getOrCreateConversation(username, "DaudM");

            return crepository.findByCustomerId(username);
        }

        return crepository.findByRealtorId(username);
    }
    public void deleteConversation(String id) {
        crepository.deleteById(id);
    }
    public Conversation getOrCreateConversation(
            String customerId,
            String realtorId) {

        Conversation conversation =
                crepository.findByCustomerIdAndRealtorId(
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

        return crepository.save(newConversation);
    }
}
