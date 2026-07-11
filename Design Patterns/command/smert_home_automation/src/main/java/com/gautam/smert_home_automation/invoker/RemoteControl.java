package com.gautam.smert_home_automation.invoker;


import com.gautam.smert_home_automation.interfaces.Command;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class RemoteControl {
    // Keeps track of the command history for the undo feature
    private final Stack<Command> commandHistory = new Stack<>();

    public String pressButton(Command command) {
        commandHistory.push(command);
        return command.execute();
    }

    public String pressUndo() {
        if (commandHistory.isEmpty()) {
            return "No commands to undo.";
        }
        Command lastCommand = commandHistory.pop();
        return lastCommand.undo();
    }
}