package org.example.messageservice.listener;

import org.example.messageservice.entity.ChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MessageStorageListener {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MessageStorageListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RabbitListener(queues = "chatStorageQueue", concurrency = "5-10")
    public void saveMessage(ChatMessage message) {
        asyncSaveMessage(message);
    }

    @Async
    public void asyncSaveMessage(ChatMessage message) {
        System.out.println("Save message: " + message);
        String collectionName = "messages_room_" + message.getRoomID();
        try {
            mongoTemplate.save(message, collectionName);
        } catch (Exception ignored) {

        }
    }
}
