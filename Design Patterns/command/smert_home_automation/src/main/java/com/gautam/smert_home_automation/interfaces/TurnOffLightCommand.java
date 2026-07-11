package com.gautam.smert_home_automation.interfaces;


import com.gautam.smert_home_automation.receiver.Light;

public class TurnOffLightCommand implements Command {
    private final Light light;

    public TurnOffLightCommand(Light light) {
        this.light = light;
    }

    @Override
    public String execute() {
        return light.turnOff();
    }

    @Override
    public String undo() {
        return light.turnOn();
    }
}
