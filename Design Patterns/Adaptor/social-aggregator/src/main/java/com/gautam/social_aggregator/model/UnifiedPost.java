package com.gautam.social_aggregator.model;

import java.time.LocalDateTime;

public class UnifiedPost {
    private String id;
    private String originalPlatform;
    private String author;
    private String content;
    private LocalDateTime timestamp;

    // Manual Constructor
    public UnifiedPost(String id, String originalPlatform, String author, String content, LocalDateTime timestamp) {
        this.id = id;
        this.originalPlatform = originalPlatform;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Manual Getters
    public String getId() { return id; }
    public String getOriginalPlatform() { return originalPlatform; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return "UnifiedPost{id='" + id + "', platform='" + originalPlatform + "', content='" + content + "'}";
    }
}