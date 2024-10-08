package org.example.recommendservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue addHotIndexQueue() {
        return new Queue("addHotIndexQueue", true);
    }
    @Bean
    public Queue historyQueue() {
        return new Queue("historyQueue", true);
    }
}
