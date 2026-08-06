package com.securerealty.backend.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
public class Documents {

    @Id
    private String id;

    private String conversationId;

    private String fileName;

    private String uploadedBy;

    private String filePath;

    private LocalDateTime uploadTime;

    public Documents() {
    }

    public Documents(String conversationId,
                    String fileName,
                    String uploadedBy,
                    String filePath,
                    LocalDateTime uploadTime) {

        this.conversationId = conversationId;
        this.fileName = fileName;
        this.uploadedBy = uploadedBy;
        this.filePath = filePath;
        this.uploadTime = uploadTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
}