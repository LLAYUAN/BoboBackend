package org.example.messageservice.listener;

import org.example.messageservice.entity.ChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Service
public class MessageBroadcastListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final BlockingQueue<ChatMessage> messageQueue = new LinkedBlockingQueue<>();

    @Autowired
    public MessageBroadcastListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "chatBroadcastQueue")
    public void receiveMessage(ChatMessage message) {
        messageQueue.offer(message);
    }

    @Scheduled(fixedRate = 500) // 每0.5秒执行一次
    public void broadcastMessages() {
        try {
            List<ChatMessage> messages = new ArrayList<>();
            messageQueue.drainTo(messages, 100); // 批量获取最多100条消息

            if (!messages.isEmpty()) {
                // 按照roomID分组并批量发送
                messages.stream()
                        .collect(Collectors.groupingBy(ChatMessage::getRoomID))
                        .forEach((roomID, roomMessages) ->
                                messagingTemplate.convertAndSend("/topic/public/" + roomID, roomMessages));
            }
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        }
    }
}
