//package org.example.messageservice.listener;
//
//import org.example.messageservice.entity.ChatMessage;
//import org.example.messageservice.repository.ChatMessageRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.Instant;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class MessageStorageListenerTest {
//
//    @Mock
//    private ChatMessageRepository chatMessageRepository;
//
//    @InjectMocks
//    private MessageStorageListener messageStorageListener;
//
//    private ChatMessage chatMessage;
//
//    @BeforeEach
//    public void setUp() {
//        chatMessage = new ChatMessage();
//        chatMessage.setId("1");
//        chatMessage.setContent("Hello, World!");
//        chatMessage.setRoomID(1);
//        chatMessage.setSender("User1");
//        chatMessage.setTimestamp(Instant.now());
//    }
//
//    @Test
//    public void testSaveMessage() {
//        messageStorageListener.saveMessage(chatMessage);
//
//        verify(chatMessageRepository, times(1)).save(chatMessage);
//    }
//}
