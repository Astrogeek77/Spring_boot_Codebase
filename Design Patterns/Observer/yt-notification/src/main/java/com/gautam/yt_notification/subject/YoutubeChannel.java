package com.gautam.yt_notification.subject;

import com.gautam.yt_notification.observer.Observer;
import java.util.ArrayList;
import java.util.List;

public class YoutubeChannel implements Subject {

    private List<Observer> subscribers = new ArrayList<>();
    private String channelName;

    public YoutubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Observer observer) {
        subscribers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifySubscribers(String videoTitle) {
        // The magic loop: Notify everyone in the list
        for (Observer observer : subscribers) {
            observer.notifed(videoTitle);
        }
    }

    // Business Logic
    public void uploadVideo(String title) {
        System.out.println("---------------------------------");
        System.out.println(channelName + " is uploading: " + title);
        notifySubscribers(title);
    }
}