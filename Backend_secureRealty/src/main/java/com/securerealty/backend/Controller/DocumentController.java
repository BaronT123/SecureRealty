package com.securerealty.backend.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.securerealty.backend.Model.Documents;
import com.securerealty.backend.Service.DocumentService;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public Documents uploadDocument(

            @RequestParam("file") MultipartFile file,

            @RequestParam("conversationId") String conversationId,

            Principal principal

    ) throws IOException {

        return service.uploadDocument(
                file,
                conversationId,
                principal.getName());

    }
    @GetMapping("/{conversationId}")
    public List<Documents> getDocuments(
            @PathVariable String conversationId) {

        return service.getDocuments(conversationId);

    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable String id) throws IOException {

        Documents document = service.getDocument(id);

        Path path = Paths.get(document.getFilePath());

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + document.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}