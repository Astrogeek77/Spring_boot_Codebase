package com.gautam.social_aggregator.adapter;

import com.gautam.social_aggregator.external.TwitterApi;
import com.gautam.social_aggregator.model.UnifiedPost;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TwitterAdapter implements SocialMediaAdapter {

    private final TwitterApi api;

    // Manual Constructor for Spring Injection
    public TwitterAdapter(TwitterApi api) {
        this.api = api;
    }

    @Override
    public List<UnifiedPost> fetchPosts() {
        return api.getTweets().stream().map(tweet -> new UnifiedPost(
                tweet.getTweet_id(),
                "Twitter",
                tweet.getUser_handle(),
                tweet.getTweet_text(),
                LocalDateTime.now()
        )).collect(Collectors.toList());
    }
}
