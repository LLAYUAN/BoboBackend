//package org.example.messageservice.listener;
//
//import ch.qos.logback.classic.Logger;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.core.AppenderBase;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class WebSocketEventListenerTest {
//
//    @Mock
//    private RabbitTemplate rabbitTemplate;
//
//    @InjectMocks
//    private WebSocketEventListener webSocketEventListener;
//
//    private TestAppender testAppender;
//
//    @BeforeEach
//    public void setUp() {
//        testAppender = new TestAppender();
//        testAppender.setName("TestAppender");
//        testAppender.start();
//
//        Logger logger = (Logger) LoggerFactory.getLogger(WebSocketEventListener.class);
//        logger.addAppender(testAppender);
//    }
//
//    @Test
//    public void testHandleWebSocketConnectListener() {
//        SessionConnectedEvent event = mock(SessionConnectedEvent.class);
//
//        webSocketEventListener.handleWebSocketConnectListener(event);
//
//        assertTrue(testAppender.contains("Received a new web socket connection", ch.qos.logback.classic.Level.INFO));
//    }
//
//    @Test
//    public void testHandleWebSocketDisconnectListener() {
//        SessionDisconnectEvent event = mock(SessionDisconnectEvent.class);
//
//        webSocketEventListener.handleWebSocketDisconnectListener(event);
//
//        assertTrue(testAppender.contains("Received a new web socket disconnection", ch.qos.logback.classic.Level.INFO));
//    }
//
//    private static class TestAppender extends AppenderBase<ILoggingEvent> {
//        private final List<ILoggingEvent> events = new ArrayList<>();
//
//        @Override
//        protected void append(ILoggingEvent eventObject) {
//            events.add(eventObject);
//        }
//
//        public boolean contains(String message, ch.qos.logback.classic.Level level) {
//            return events.stream().anyMatch(event -> event.getMessage().contains(message) && event.getLevel().equals(level));
//        }
//    }
//}
