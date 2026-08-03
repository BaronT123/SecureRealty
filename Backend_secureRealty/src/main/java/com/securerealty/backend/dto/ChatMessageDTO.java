package com.securerealty.backend.dto;

public class ChatMessageDTO {

    private String conversationId;
    private String sender;
    private String receiver;
    private String message;

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String conversationId,
                          String sender,
                          String receiver,
                          String message) {

        this.conversationId = conversationId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}