package com.gautam.yt_notification.controller;

import com.gautam.yt_notification.observer.Subscriber;
import com.gautam.yt_notification.subject.YoutubeChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeController {

    @GetMapping("/api/youtube/upload")
    public String demoObserver(@RequestParam String videoTitle) {

        // 1. Create the Channel (Subject)
        YoutubeChannel myChannel = new YoutubeChannel("Gautam's Tech");

        // 2. Create Subscribers (Observers)
        Subscriber user1 = new Subscriber("Alice");
        Subscriber user2 = new Subscriber("Bob");
        Subscriber user3 = new Subscriber("Charlie");

        // 3. Register them
        myChannel.subscribe(user1);
        myChannel.subscribe(user2);
        myChannel.subscribe(user3);

        // 4. ACTION: Upload a video
        // This should automatically trigger notifications for Alice, Bob, and Charlie
        myChannel.uploadVideo(videoTitle);

        // 5. Unsubscribe Bob and upload another
        myChannel.unsubscribe(user2);
        myChannel.uploadVideo("Java 17 Features (Bonus)");

        return "Check the console logs to see the Observer Pattern in action!";
    }
}
