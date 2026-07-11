package com.gautam.document_template_system.registry;

import com.gautam.document_template_system.model.Document;
import com.gautam.document_template_system.model.Invoice;
import com.gautam.document_template_system.model.Report;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DocumentRegistry {

    // The cache holding our prototypes
    private final Map<String, Document> prototypes = new HashMap<>();

    @PostConstruct
    public void initPrototypes() {
        // Create the "expensive" objects once and store them
        Invoice invoicePrototype = new Invoice();
        Report reportPrototype = new Report();

        prototypes.put("invoice", invoicePrototype);
        prototypes.put("report", reportPrototype);
    }

    // The client calls this method to get a brand new, cloned object
    public Document getClonedDocument(String type) {
        Document prototype = prototypes.get(type.toLowerCase());
        if (prototype != null) {
            return prototype.clone();
        }
        throw new IllegalArgumentException("Unknown document type: " + type);
    }
}
