package com.chat.app.controller;

import com.chat.app.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") // Matches client: stompClient.send('/app/sendMessage', ...)
    @SendTo("/topic/messages")      // Matches client: stompClient.subscribe('/topic/messages', ...)
    public ChatMessage sendMessage(ChatMessage message) {

        // 1. Capture current Date and Time
        LocalDateTime now = LocalDateTime.now();

        // 2. Format it (e.g., "2023-10-05 14:30")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 3. Set the timestamp manually
        message.setTimestamp(now.format(formatter));

        return message;
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
}