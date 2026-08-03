package com.securerealty.backend.Service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.securerealty.backend.Repository.*;

import com.securerealty.backend.Model.ChatMessage;

@Service
public class ChatService {
	private final ChatRepository repository;

    public ChatService(ChatRepository repository) {
        this.repository = repository;
    }

    public ChatMessage processMessage(ChatMessage message) {
    	message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        System.out.println("Timestamp before save: " + message.getTimestamp());

        ChatMessage saved = repository.save(message);

        System.out.println("Timestamp after save: " + saved.getTimestamp());
        return saved;

    }
    public List<ChatMessage> getConversationMessages(
            String conversationId){

        return repository
                .findByConversationIdOrderByTimestampAsc(
                        conversationId);

    }
}
