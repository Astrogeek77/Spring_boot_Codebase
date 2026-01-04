package com.gautam.social_aggregator.service;

import com.gautam.social_aggregator.adapter.SocialMediaAdapter;
import com.gautam.social_aggregator.model.UnifiedPost;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocialAggregatorService {

    private final List<SocialMediaAdapter> adapters;

    // Manual Constructor: Spring passes the list of adapters here
    public SocialAggregatorService(List<SocialMediaAdapter> adapters) {
        this.adapters = adapters;
    }

    public List<UnifiedPost> getAllFeeds() {
        List<UnifiedPost> aggregator = new ArrayList<>();
        for (SocialMediaAdapter adapter : adapters) {
            aggregator.addAll(adapter.fetchPosts());
        }
        return aggregator;
    }
}