package org.example.messageservice.listener;

import org.example.messageservice.entity.ChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RabbitMQListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "chatQueue")
    public void receiveMessage(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/public/" + message.getRoomID(), message);
    }
}
