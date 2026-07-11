package com.gautam.undo_system.pattern;


public class ArticleMemento {
    private final String title;
    private final String content;

    public ArticleMemento(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Only Getters, no Setters! The state must remain frozen.
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}