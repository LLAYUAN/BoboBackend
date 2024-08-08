//package org.example.messageservice.repository;
//
//import org.example.messageservice.entity.ChatMessage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.Instant;
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//class ChatMessageRepositoryTest {
//
//    @Mock
//    private ChatMessageRepository chatMessageRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Create test data
//        ChatMessage message1 = new ChatMessage();
//        message1.setType(ChatMessage.MessageType.CHAT);
//        message1.setContent("Hello!");
//        message1.setSender("user1");
//        message1.setRoomID(1);
//        message1.setTimestamp(Instant.parse("2023-01-01T10:00:00Z"));
//
//        ChatMessage message2 = new ChatMessage();
//        message2.setType(ChatMessage.MessageType.CHAT);
//        message2.setContent("Hi there!");
//        message2.setSender("user2");
//        message2.setRoomID(1);
//        message2.setTimestamp(Instant.parse("2023-01-01T11:00:00Z"));
//
//        Page<ChatMessage> mockPage = new PageImpl<>(Arrays.asList(message2, message1), PageRequest.of(0, 10), 2);
//
//        // Mock repository method
//        when(chatMessageRepository.findByRoomIDAndTimestampLessThanOrderByTimestampDesc(1, Instant.parse("2023-01-01T11:30:00Z"), PageRequest.of(0, 10)))
//                .thenReturn(mockPage);
//
//        // Mock delete method behavior
//        doNothing().when(chatMessageRepository).deleteByTimestampLessThan(Instant.parse("2023-01-01T11:00:00Z"));
//    }
//
//    @Test
//    void findByRoomIDAndTimestampLessThanOrderByTimestampDesc() {
//        // Perform the repository method call
//        Integer roomID = 1;
//        Instant timestamp = Instant.parse("2023-01-01T11:30:00Z");
//        PageRequest pageable = PageRequest.of(0, 10); // Pageable for the first page with 10 items
//
//        Page<ChatMessage> resultPage = chatMessageRepository.findByRoomIDAndTimestampLessThanOrderByTimestampDesc(roomID, timestamp, pageable);
//
//        // Assertions
//        assertEquals(2, resultPage.getTotalElements()); // Assert the total number of elements in the result
//        assertEquals("Hi there!", resultPage.getContent().get(0).getContent()); // Assert the content of the first message
//        assertEquals("Hello!", resultPage.getContent().get(1).getContent()); // Assert the content of the second message
//        assertTrue(resultPage.getContent().get(0).getTimestamp().isAfter(resultPage.getContent().get(1).getTimestamp())); // Assert ordering by timestamp
//    }
//
//    @Test
//    void deleteByTimestampLessThan() {
//        // Perform the repository method call
//        Instant cutoffTime = Instant.parse("2023-01-01T11:00:00Z");
//
//        // Call the method
//        chatMessageRepository.deleteByTimestampLessThan(cutoffTime);
//
//        // Verify that the delete method was called with the correct parameter
//        verify(chatMessageRepository, times(1)).deleteByTimestampLessThan(cutoffTime);
//    }
//}
