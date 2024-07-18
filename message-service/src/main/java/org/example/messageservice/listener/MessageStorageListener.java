package org.example.messageservice.listener;

import org.example.messageservice.entity.ChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Service
public class MessageStorageListener {

    private final MongoTemplate mongoTemplate;
    private final BlockingQueue<ChatMessage> messageQueue = new LinkedBlockingQueue<>();

    @Autowired
    public MessageStorageListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RabbitListener(queues = "chatStorageQueue", concurrency = "5-10")
    public void saveMessage(ChatMessage message) {
        messageQueue.offer(message);
    }

    @Scheduled(fixedRate = 200) // Every 0.2 seconds
    public void saveMessagesInBatches() {
        try {
            List<ChatMessage> messages = new ArrayList<>();
            messageQueue.drainTo(messages, 200); // Batch size of 100 messages

            if (!messages.isEmpty()) {
                // Group by roomID and save batches
                messages.stream()
                        .collect(Collectors.groupingBy(ChatMessage::getRoomID))
                        .forEach((roomID, roomMessages) -> saveBatchMessages(roomMessages));
            }

        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    public void saveBatchMessages(List<ChatMessage> messages) {
        System.out.println("Saving batch of " + messages.size() + " messages");
        if (messages.isEmpty()) {
            return;
        }
        String collectionName = "messages_room_" + messages.get(0).getRoomID();
        try {
            mongoTemplate.insert(messages, collectionName);
            System.out.println("Saved batch of " + messages.size() + " messages to " + collectionName);
        } catch (Exception e) {
            System.err.println("Failed to save batch of messages: " + e.getMessage());
            // Consider logging the error and the failed messages for retry logic
        }
    }
}
