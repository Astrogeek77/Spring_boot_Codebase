package com.gautam.social_aggregator.adapter;

import com.gautam.social_aggregator.external.InstagramApi;
import com.gautam.social_aggregator.model.UnifiedPost;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InstagramAdapter implements SocialMediaAdapter {

    private final InstagramApi api;

    // Manual Constructor for Spring Injection
    public InstagramAdapter(InstagramApi api) {
        this.api = api;
    }

    @Override
    public List<UnifiedPost> fetchPosts() {
        return api.getInstagramPosts().stream().map(post -> new UnifiedPost(
                post.getPostId(),      // Now this method definitely exists
                "Instagram",
                post.getPostedBy(),
                post.getStatus(),
                LocalDateTime.now()
        )).collect(Collectors.toList());
    }
}
