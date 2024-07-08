package org.example.messageservice.repository;

import org.example.messageservice.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    Page<ChatMessage> findByRoomIDAndTimestampLessThanOrderByTimestampDesc(Integer roomID, Instant timestamp, Pageable pageable);
}
