package com.securerealty.backend.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "conversations")
public class Conversation {
	@Id
    private String id;
    private String customerId;
    private String realtorId;
    private LocalDateTime createdAt;

    public Conversation() {
    }

    public Conversation(String id, String customerId, String realtorId, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.realtorId = realtorId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRealtorId() {
        return realtorId;
    }

    public void setRealtorId(String realtorId) {
        this.realtorId = realtorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}