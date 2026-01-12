package com.gautam.yt_notification.subject;

import com.gautam.yt_notification.observer.Observer;

public interface Subject {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifySubscribers(String videoTitle);
}
