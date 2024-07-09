package org.example.messageservice.service;

import org.example.messageservice.entity.ChatMessage;

import java.time.Instant;
import java.util.List;

public interface ChatService {
    List<ChatMessage> getHistoryMessages(Integer roomID, Instant timestamp);
}
