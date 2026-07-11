package com.gautam.smert_home_automation;

import com.gautam.smert_home_automation.interfaces.Command;
import com.gautam.smert_home_automation.interfaces.TurnOffLightCommand;
import com.gautam.smert_home_automation.interfaces.TurnOnLightCommand;
import com.gautam.smert_home_automation.invoker.RemoteControl;
import com.gautam.smert_home_automation.receiver.Light;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/smarthome")
public class SmartHomeController {

    private final RemoteControl remoteControl;
    private final Light livingRoomLight;

    @Autowired
    public SmartHomeController(RemoteControl remoteControl, Light livingRoomLight) {
        this.remoteControl = remoteControl;
        this.livingRoomLight = livingRoomLight;
    }

    @PostMapping("/light/on")
    public String turnOnLight() {
        Command turnOn = new TurnOnLightCommand(livingRoomLight);
        return remoteControl.pressButton(turnOn);
    }

    @PostMapping("/light/off")
    public String turnOffLight() {
        Command turnOff = new TurnOffLightCommand(livingRoomLight);
        return remoteControl.pressButton(turnOff);
    }

    @PostMapping("/undo")
    public String undoLastAction() {
        return remoteControl.pressUndo();
    }
}
