package org.example.messageservice.service;

import org.example.messageservice.entity.ChatMessage;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ChatService {
    CompletableFuture<List<ChatMessage>> getHistoryMessages(Integer roomID, Instant timestamp);
    void deleteOldMessages(Instant cutoffTime);
    void deleteRoomMessages(Integer roomID);
}
