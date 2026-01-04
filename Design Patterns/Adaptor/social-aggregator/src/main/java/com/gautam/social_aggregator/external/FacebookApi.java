package com.gautam.social_aggregator.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FacebookApi {

    // Inner class representing the external data structure
    public static class FacebookPost {
        private String postId;
        private String status;
        private String postedBy;

        public FacebookPost(String postId, String status, String postedBy) {
            this.postId = postId;
            this.status = status;
            this.postedBy = postedBy;
        }

        // --- Manual Getters (These fix your error) ---
        public String getPostId() { return postId; }
        public String getStatus() { return status; }
        public String getPostedBy() { return postedBy; }
    }

    public List<FacebookPost> getFacebookPosts() {
        return List.of(new FacebookPost("fb100", "Just updated my status", "MetaUser"));
    }
}
