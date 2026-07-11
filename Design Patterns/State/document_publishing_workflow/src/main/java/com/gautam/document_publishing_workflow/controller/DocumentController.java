package com.gautam.document_publishing_workflow.controller;

import com.gautam.document_publishing_workflow.Model.Document;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    // Simulating a single document in a database for this demonstration
    private Document currentDocument = new Document("Initial System Architecture Guidelines");

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("currentState", currentDocument.getStateName());
        return response;
    }

    @PostMapping("/submit")
    public Map<String, String> submit() {
        Map<String, String> response = new HashMap<>();
        response.put("message", currentDocument.submitForReview());
        response.put("newState", currentDocument.getStateName());
        return response;
    }

    @PostMapping("/approve")
    public Map<String, String> approve() {
        Map<String, String> response = new HashMap<>();
        response.put("message", currentDocument.approve());
        response.put("newState", currentDocument.getStateName());
        return response;
    }

    @PostMapping("/reject")
    public Map<String, String> reject() {
        Map<String, String> response = new HashMap<>();
        response.put("message", currentDocument.reject());
        response.put("newState", currentDocument.getStateName());
        return response;
    }
}
