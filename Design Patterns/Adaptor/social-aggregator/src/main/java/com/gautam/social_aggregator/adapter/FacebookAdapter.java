package com.gautam.social_aggregator.adapter;

import com.gautam.social_aggregator.external.FacebookApi;
import com.gautam.social_aggregator.model.UnifiedPost;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacebookAdapter implements SocialMediaAdapter {

    private final FacebookApi api;

    // Manual Constructor for Spring Injection
    public FacebookAdapter(FacebookApi api) {
        this.api = api;
    }

    @Override
    public List<UnifiedPost> fetchPosts() {
        return api.getFacebookPosts().stream().map(post -> new UnifiedPost(
                post.getPostId(),      // Now this method definitely exists
                "Facebook",
                post.getPostedBy(),
                post.getStatus(),
                LocalDateTime.now()
        )).collect(Collectors.toList());
    }
}
