package com.gautam.social_aggregator.external;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TwitterApi {

    public static class TwitterTweet {
        private String tweet_id;
        private String tweet_text;
        private String user_handle;

        public TwitterTweet(String tweet_id, String tweet_text, String user_handle) {
            this.tweet_id = tweet_id;
            this.tweet_text = tweet_text;
            this.user_handle = user_handle;
        }

        // --- Manual Getters ---
        public String getTweet_id() { return tweet_id; }
        public String getTweet_text() { return tweet_text; }
        public String getUser_handle() { return user_handle; }
    }

    public List<TwitterTweet> getTweets() {
        return List.of(new TwitterTweet("tw1", "Spring Boot is awesome!", "@dev_gautam"));
    }
}
