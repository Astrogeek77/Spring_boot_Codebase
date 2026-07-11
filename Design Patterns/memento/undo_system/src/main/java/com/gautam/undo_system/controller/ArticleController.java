package com.gautam.undo_system.controller;

import com.gautam.undo_system.model.Article;
import com.gautam.undo_system.pattern.ArticleMemento;
import com.gautam.undo_system.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final HistoryService historyService;

    // Simulating a single working document for demonstration
    private final Article currentArticle = new Article("Untitled", "");

    @Autowired
    public ArticleController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/current")
    public Article getCurrentArticle() {
        return currentArticle;
    }

    @PostMapping("/write")
    public Article write(@RequestParam String title, @RequestParam String content) {
        // Before making a change, save the current state to the history!
        historyService.saveState(currentArticle.save());

        // Now update the article
        currentArticle.setContent(title, content);
        return currentArticle;
    }

    @PostMapping("/undo")
    public Map<String, Object> undo() {
        Map<String, Object> response = new HashMap<>();

        ArticleMemento previousState = historyService.undo();

        if (previousState != null) {
            currentArticle.restore(previousState);
            response.put("message", "Undo successful");
            response.put("article", currentArticle);
        } else {
            response.put("message", "Nothing to undo. History is empty.");
            response.put("article", currentArticle);
        }

        return response;
    }
}
