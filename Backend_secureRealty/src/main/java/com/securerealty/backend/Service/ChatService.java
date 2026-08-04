package com.securerealty.backend.Service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.securerealty.backend.Repository.*;
import com.securerealty.backend.dto.EncryptedData;
import com.securerealty.backend.Model.ChatMessage;

@Service
public class ChatService {
	private final ChatRepository repository;
	private final EncryptionService encryptionService;

    public ChatService(ChatRepository repository, EncryptionService encryptionService) {
        this.repository = repository;
        this.encryptionService = encryptionService;
    }

    public ChatMessage processMessage(ChatMessage message) throws Exception {
    	message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        System.out.println("Timestamp before save: " + message.getTimestamp());
        EncryptedData encrypted =
                encryptionService.encrypt(message.getMessage());

        message.setMessage(encrypted.getCipherText());

        message.setIv(encrypted.getIv());

        ChatMessage saved = repository.save(message);

        System.out.println("Timestamp after save: " + saved.getTimestamp());
        saved.setMessage(
        	    encryptionService.decrypt(
        	        saved.getMessage(),
        	        saved.getIv()
        	    )
        	);
        return saved;

    }
    public List<ChatMessage> getConversationMessages(String conversationId)
            throws Exception {

        List<ChatMessage> messages =
                repository.findByConversationIdOrderByTimestampAsc(conversationId);

        for (ChatMessage message : messages) {

            message.setMessage(
                    encryptionService.decrypt(
                            message.getMessage(),
                            message.getIv()));

        }

        return messages;
    }
}
