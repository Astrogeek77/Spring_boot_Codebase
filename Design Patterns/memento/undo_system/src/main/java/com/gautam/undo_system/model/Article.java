package com.gautam.undo_system.model;


import com.gautam.undo_system.pattern.ArticleMemento;

public class Article {
    private String title;
    private String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }

    // Creates a snapshot of the current state
    public ArticleMemento save() {
        return new ArticleMemento(this.title, this.content);
    }

    // Restores the state from a snapshot
    public void restore(ArticleMemento memento) {
        this.title = memento.getTitle();
        this.content = memento.getContent();
    }
}
