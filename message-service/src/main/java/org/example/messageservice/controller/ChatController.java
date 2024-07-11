package org.example.messageservice.controller;

import org.example.messageservice.entity.ChatMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ChatController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(chatMessage.getContent());
        rabbitTemplate.convertAndSend("chatExchange", "",chatMessage);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println( ": " + chatMessage.getSender());
        chatMessage.setContent(chatMessage.getSender() + " joined");
        rabbitTemplate.convertAndSend("chatExchange", "", chatMessage);
    }
}
