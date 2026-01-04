package com.gautam.social_aggregator.adapter;

import com.gautam.social_aggregator.model.UnifiedPost;
import java.util.List;

public interface SocialMediaAdapter {
    List<UnifiedPost> fetchPosts();
}