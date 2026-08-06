package com.securerealty.backend.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.securerealty.backend.Model.Documents;

public interface DocumentRepository
        extends MongoRepository<Documents, String> {

    List<Documents> findByConversationIdOrderByUploadTimeDesc(
            String conversationId);

}