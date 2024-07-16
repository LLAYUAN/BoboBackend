package org.example.messageservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RabbitMQConfigTest {

    @InjectMocks
    private RabbitMQConfig rabbitMQConfig;

    @Mock
    private ConnectionFactory connectionFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChatBroadcastQueue() {
        Queue queue = rabbitMQConfig.chatBroadcastQueue();
        assertNotNull(queue);
        assertEquals("chatBroadcastQueue", queue.getName());
        assertEquals(true, queue.isDurable());
    }

    @Test
    public void testChatStorageQueue() {
        Queue queue = rabbitMQConfig.chatStorageQueue();
        assertNotNull(queue);
        assertEquals("chatStorageQueue", queue.getName());
        assertEquals(true, queue.isDurable());
    }

    @Test
    public void testJsonMessageConverter() {
        Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) rabbitMQConfig.jsonMessageConverter();
        assertNotNull(converter);
    }

    @Test
    public void testChatExchange() {
        FanoutExchange exchange = rabbitMQConfig.chatExchange();
        assertNotNull(exchange);
        assertEquals("chatExchange", exchange.getName());
    }

    @Test
    public void testBroadcastBinding() {
        FanoutExchange exchange = rabbitMQConfig.chatExchange();
        Queue queue = rabbitMQConfig.chatBroadcastQueue();
        Binding binding = rabbitMQConfig.broadcastBinding(exchange, queue);
        assertNotNull(binding);
        assertEquals("chatBroadcastQueue", binding.getDestination());
        assertEquals("chatExchange", binding.getExchange());
    }

    @Test
    public void testStorageBinding() {
        FanoutExchange exchange = rabbitMQConfig.chatExchange();
        Queue queue = rabbitMQConfig.chatStorageQueue();
        Binding binding = rabbitMQConfig.storageBinding(exchange, queue);
        assertNotNull(binding);
        assertEquals("chatStorageQueue", binding.getDestination());
        assertEquals("chatExchange", binding.getExchange());
    }

    @Test
    public void testRabbitTemplate() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        assertNotNull(template);
        assertEquals(converter, template.getMessageConverter());
    }
}
