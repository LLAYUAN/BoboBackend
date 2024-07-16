package org.example.messageservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WebSocketConfigTest {

    @InjectMocks
    private WebSocketConfig webSocketConfig;

    @Mock
    private StompEndpointRegistry stompEndpointRegistry;

    @Mock
    private MessageBrokerRegistry messageBrokerRegistry;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterStompEndpoints() {
        // Mocking StompWebSocketEndpointRegistration for chain calls
        StompWebSocketEndpointRegistration mockEndpointRegistration = mock(StompWebSocketEndpointRegistration.class, RETURNS_SELF);

        when(stompEndpointRegistry.addEndpoint(any(String.class))).thenReturn(mockEndpointRegistration);

        webSocketConfig.registerStompEndpoints(stompEndpointRegistry);

        verify(stompEndpointRegistry).addEndpoint("/chat");
        verify(mockEndpointRegistration).setAllowedOrigins("http://localhost:3000");
        verify(mockEndpointRegistration).withSockJS();
    }

    @Test
    void testConfigureMessageBroker() {
        webSocketConfig.configureMessageBroker(messageBrokerRegistry);

        verify(messageBrokerRegistry).setApplicationDestinationPrefixes("/app");
        verify(messageBrokerRegistry).enableSimpleBroker("/topic");
    }
}
