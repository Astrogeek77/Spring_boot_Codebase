package com.gautam.document_template_system.model;


public abstract class Document implements Cloneable {
    private String type;
    private String title;
    private String content;

    // Standard Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override
    public Document clone() {
        try {
            // Performs a shallow copy. For objects with complex nested structures,
            // you would implement a deep copy here.
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed", e);
        }
    }
}
