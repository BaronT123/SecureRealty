package com.securerealty.backend.Controller;

import com.securerealty.backend.Model.ChatMessage;
import com.securerealty.backend.Service.ChatService;
import com.securerealty.backend.dto.ChatMessageDTO;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService,
                          SimpMessagingTemplate messagingTemplate) {

        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessageDTO messageDTO) {

        ChatMessage message = new ChatMessage();

        message.setConversationId(messageDTO.getConversationId());
        message.setSender(messageDTO.getSender());
        message.setReceiver(messageDTO.getReceiver());
        message.setMessage(messageDTO.getMessage());

        ChatMessage savedMessage = chatService.processMessage(message);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + savedMessage.getConversationId(),
                savedMessage
        );
    }
}