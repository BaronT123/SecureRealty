package com.securerealty.backend.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.securerealty.backend.Model.ChatMessage;
import com.securerealty.backend.Service.ChatService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final ChatService chatService;

    public MessageController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{conversationId}")
    public List<ChatMessage> getMessages(
            @PathVariable String conversationId) throws Exception{

        return chatService.getConversationMessages(conversationId);
    }
}