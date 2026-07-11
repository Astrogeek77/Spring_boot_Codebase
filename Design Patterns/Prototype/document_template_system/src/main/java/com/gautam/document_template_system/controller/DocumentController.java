package com.gautam.document_template_system.controller;

import com.gautam.document_template_system.model.Document;
import com.gautam.document_template_system.registry.DocumentRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentRegistry documentRegistry;

    @Autowired
    public DocumentController(DocumentRegistry documentRegistry) {
        this.documentRegistry = documentRegistry;
    }

    @PostMapping("/generate/{type}")
    public Document generateDocument(
            @PathVariable String type,
            @RequestParam(required = false) String customTitle) {

        // 1. Fetch a clone from the registry
        Document clonedDocument = documentRegistry.getClonedDocument(type);

        // 2. Customize the clone without affecting the original prototype
        if (customTitle != null && !customTitle.isEmpty()) {
            clonedDocument.setTitle(customTitle);
        }

        return clonedDocument;
    }
}
