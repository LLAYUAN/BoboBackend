package org.example.messageservice.serviceimpl;

import org.example.messageservice.entity.ChatMessage;
import org.example.messageservice.repository.ChatMessageRepository;
import org.example.messageservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Override
    public List<ChatMessage> getHistoryMessages(Integer roomID, Instant timestamp){
        Pageable pageable = PageRequest.of(0, 20);  // 查询时指定一页的大小
        List<ChatMessage> messages=chatMessageRepository.findByRoomIDAndTimestampLessThanOrderByTimestampDesc(roomID, timestamp, pageable).getContent();
        return messages;

    }
}