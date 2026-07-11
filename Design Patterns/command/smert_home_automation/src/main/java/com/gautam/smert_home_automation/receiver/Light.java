package com.gautam.smert_home_automation.receiver;

import org.springframework.stereotype.Component;

@Component
public class Light {
    private boolean isOn = false;

    public String turnOn() {
        this.isOn = true;
        return "The Light is now ON";
    }

    public String turnOff() {
        this.isOn = false;
        return "The Light is now OFF";
    }

    public boolean isOn() {
        return isOn;
    }
}
