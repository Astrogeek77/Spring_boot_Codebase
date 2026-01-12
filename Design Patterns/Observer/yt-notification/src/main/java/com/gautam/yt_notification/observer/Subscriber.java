package com.gautam.yt_notification.observer;

public class Subscriber implements Observer {

    private String name;

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void notifed(String videoTitle) {
        System.out.println("Hey " + name + ", New Video Alert: " + videoTitle);
    }
}