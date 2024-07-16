package org.example.messageservice.serviceimpl;

import org.example.messageservice.entity.ChatMessage;
import org.example.messageservice.repository.ChatMessageRepository;
import org.example.messageservice.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceImplTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatServiceImpl chatService;

    private List<ChatMessage> sampleMessages;

    @BeforeEach
    public void setUp() {
        ChatMessage msg1 = new ChatMessage();
        msg1.setId("1");
        msg1.setContent("Hello");
        msg1.setRoomID(1);
        msg1.setTimestamp(Instant.now());

        ChatMessage msg2 = new ChatMessage();
        msg2.setId("2");
        msg2.setContent("Hi");
        msg2.setRoomID(1);
        msg2.setTimestamp(Instant.now());

        sampleMessages = Arrays.asList(msg1, msg2);
    }

    @Test
    public void testGetHistoryMessages() {
        Instant timestamp = Instant.now();
        Pageable pageable = PageRequest.of(0, 20);
        Page<ChatMessage> page = new PageImpl<>(sampleMessages);

        when(chatMessageRepository.findByRoomIDAndTimestampLessThanOrderByTimestampDesc(eq(1), eq(timestamp), any(Pageable.class))).thenReturn(page);

        List<ChatMessage> result = chatService.getHistoryMessages(1, timestamp);

        assertEquals(2, result.size());
        assertEquals("Hello", result.get(0).getContent());
        assertEquals("Hi", result.get(1).getContent());
        verify(chatMessageRepository, times(1)).findByRoomIDAndTimestampLessThanOrderByTimestampDesc(eq(1), eq(timestamp), any(Pageable.class));
    }

    @Test
    public void testDeleteOldMessages() {
        Instant cutoffTime = Instant.now();

        doNothing().when(chatMessageRepository).deleteByTimestampLessThan(cutoffTime);

        chatService.deleteOldMessages(cutoffTime);

        verify(chatMessageRepository, times(1)).deleteByTimestampLessThan(cutoffTime);
    }
}
