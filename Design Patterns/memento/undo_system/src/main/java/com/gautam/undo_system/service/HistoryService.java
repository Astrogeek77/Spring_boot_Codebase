package com.gautam.undo_system.service;


import com.gautam.undo_system.pattern.ArticleMemento;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class HistoryService {

    // Uses a Stack (LIFO) to keep track of the history for our Undo feature
    private final Stack<ArticleMemento> history = new Stack<>();

    public void saveState(ArticleMemento memento) {
        history.push(memento);
    }

    public ArticleMemento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null; // Nothing left to undo
    }
}
