package org.example.messageservice.listener;

import org.example.messageservice.entity.ChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageBroadcastListener {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageBroadcastListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "chatBroadcastQueue")
    public void receiveMessage(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/public/" + message.getRoomID(), message);
    }
}