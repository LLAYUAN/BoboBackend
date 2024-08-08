//package org.example.messageservice.controller;
//
//import org.example.messageservice.entity.ChatMessage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ChatControllerTest {
//
//    @Mock
//    private RabbitTemplate rabbitTemplate;
//
//    @InjectMocks
//    private ChatController chatController;
//
//    private ChatMessage chatMessage;
//
//    @BeforeEach
//    public void setUp() {
//        chatMessage = new ChatMessage();
//        chatMessage.setContent("Hello, World!");
//        chatMessage.setSender("User1");
//    }
//
//    @Test
//    public void testSendMessage() {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
//
//        chatController.sendMessage(chatMessage, headerAccessor);
//
//        verify(rabbitTemplate, times(1)).convertAndSend(eq("chatExchange"), eq(""), eq(chatMessage));
//    }
//
//    @Test
//    public void testAddUser() {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
//
//        chatController.addUser(chatMessage, headerAccessor);
//
//        verify(rabbitTemplate, times(1)).convertAndSend(eq("chatExchange"), eq(""), any(ChatMessage.class));
//        assertEquals(chatMessage.getSender() + " joined", chatMessage.getContent());
//    }
//
//    @Test
//    public void testTestEndpoint() {
//        String response = chatController.test();
//        assertEquals("test", response);
//    }
//}
