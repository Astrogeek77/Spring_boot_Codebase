package com.gautam.document_template_system.model;

public class Invoice extends Document {
    public Invoice() {
        setType("Invoice");
        setTitle("Standard Invoice Template");
        setContent("Billed To: [Name] | Total: $0.00 | Tax: 0%");
    }
}
