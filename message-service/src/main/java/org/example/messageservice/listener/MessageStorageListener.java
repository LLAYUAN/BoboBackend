package org.example.messageservice.listener;

import org.example.messageservice.entity.ChatMessage;
import org.example.messageservice.repository.ChatMessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageStorageListener {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public MessageStorageListener(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @RabbitListener(queues = "chatStorageQueue")
    public void saveMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }
}