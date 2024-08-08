//package org.example.messageservice.listener;
//
//import org.example.messageservice.entity.ChatMessage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//
//import java.time.Instant;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class MessageBroadcastListenerTest {
//
//    @Mock
//    private SimpMessagingTemplate messagingTemplate;
//
//    @InjectMocks
//    private MessageBroadcastListener messageBroadcastListener;
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
//    public void testReceiveMessage() {
//        messageBroadcastListener.receiveMessage(chatMessage);
//
//        verify(messagingTemplate, times(1)).convertAndSend("/topic/public/" + chatMessage.getRoomID(), chatMessage);
//    }
//}
