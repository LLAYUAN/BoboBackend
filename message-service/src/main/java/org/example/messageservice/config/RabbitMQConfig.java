package org.example.messageservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue chatBroadcastQueue() {
        return new Queue("chatBroadcastQueue", true);
    }

    @Bean
    public Queue chatStorageQueue() {
        return new Queue("chatStorageQueue", true);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public FanoutExchange chatExchange() {
        return new FanoutExchange("chatExchange");
    }
    @Bean
    public Binding broadcastBinding(FanoutExchange chatExchange, Queue chatBroadcastQueue) {
        return BindingBuilder.bind(chatBroadcastQueue).to(chatExchange);
    }

    @Bean
    public Binding storageBinding(FanoutExchange chatExchange, Queue chatStorageQueue) {
        return BindingBuilder.bind(chatStorageQueue).to(chatExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}