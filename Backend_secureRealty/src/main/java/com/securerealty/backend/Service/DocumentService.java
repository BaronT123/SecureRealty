package com.securerealty.backend.Service;

import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.securerealty.backend.Model.Documents;
import com.securerealty.backend.Repository.DocumentRepository;

@Service
public class DocumentService {

    private final DocumentRepository repository;

    private final Path uploadPath = Paths.get("uploads");

    public DocumentService(DocumentRepository repository)
            throws IOException {

        this.repository = repository;

        Files.createDirectories(uploadPath);
    }

    public Documents uploadDocument(
            MultipartFile file,
            String conversationId,
            String uploadedBy) throws IOException {

        // Generate unique filename
        String fileName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Save PDF to uploads/
        Path destination = uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING);

        // Save metadata
        Documents document = new Documents();

        document.setConversationId(conversationId);
        document.setFileName(file.getOriginalFilename());
        document.setUploadedBy(uploadedBy);
        document.setFilePath(destination.toString());
        document.setUploadTime(LocalDateTime.now());

        return repository.save(document);
    }
    public List<Documents> getDocuments(String conversationId) {

        return repository.findByConversationIdOrderByUploadTimeDesc(
                conversationId);

    }
    public Documents getDocument(String id) {

        return repository.findById(id).orElse(null);

    }
}