package org.example.messageservice.controller;

import org.example.messageservice.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage/{roomID}")
    @SendTo("/topic/public/{roomID}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String roomNumber = headerAccessor.getDestination().split("/")[3];
        System.out.println("Received message in room " + roomNumber + ": " + chatMessage.getContent());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{roomID}")
    @SendTo("/topic/public/{roomID}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String roomNumber = headerAccessor.getDestination().split("/")[3];
        System.out.println("Received addUser in room " + roomNumber + ": " + chatMessage.getSender());
        chatMessage.setContent(chatMessage.getSender() + " joined");
        return chatMessage;
    }
}
