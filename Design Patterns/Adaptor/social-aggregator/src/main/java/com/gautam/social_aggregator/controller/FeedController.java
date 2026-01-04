package com.gautam.social_aggregator.controller;

import com.gautam.social_aggregator.model.UnifiedPost;
import com.gautam.social_aggregator.service.SocialAggregatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedController {

    private final SocialAggregatorService service;

    // Manual Constructor for Injection
    public FeedController(SocialAggregatorService service) {
        this.service = service;
    }

    @GetMapping("/api/feed")
    public List<UnifiedPost> getFeed() {
        return service.getAllFeeds();
    }
}