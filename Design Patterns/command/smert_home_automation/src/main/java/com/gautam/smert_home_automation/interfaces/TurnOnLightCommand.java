package com.gautam.smert_home_automation.interfaces;


import com.gautam.smert_home_automation.receiver.Light;

public class TurnOnLightCommand implements Command {
    private final Light light;

    public TurnOnLightCommand(Light light) {
        this.light = light;
    }

    @Override
    public String execute() {
        return light.turnOn();
    }

    @Override
    public String undo() {
        return light.turnOff();
    }
}
