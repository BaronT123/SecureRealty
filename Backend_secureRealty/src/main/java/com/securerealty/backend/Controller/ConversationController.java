package com.securerealty.backend.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.securerealty.backend.Model.Conversation;
import com.securerealty.backend.Service.ConversationService;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService service;

    public ConversationController(ConversationService service) {
        this.service = service;
    }
    @PostMapping
    public Conversation getOrCreateConversation(@RequestBody Conversation conversation) {

        return service.getOrCreateConversation(
                conversation.getCustomerId(),
                conversation.getRealtorId());

    }

    

    @GetMapping
    public List<Conversation> getAllConversations() {

        return service.getAllConversations();

    }

}
