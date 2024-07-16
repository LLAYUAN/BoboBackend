package org.example.recommendservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.example.recommendservice.DTO.AddHotIndex;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
class RecommendServiceApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void testSendM2Q() throws IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        factory.setPort(5672);
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        factory.setVirtualHost("/");
//        Connection connection = factory.newConnection();
//
//        Channel channel = connection.createChannel();
//
//        String queueName = "simple.queue";
//        channel.queueDeclare(queueName, false, false, false, null);
//
//        String message = "Hello, RabbitMQ!";
//        channel.basicPublish("", queueName, null, message.getBytes());
//
//        channel.close();
//        connection.close();
        AddHotIndex addHotIndex = new AddHotIndex();
        addHotIndex.setRoomId(1);
        String json = objectMapper.writeValueAsString(addHotIndex);
        rabbitTemplate.convertAndSend("addHotIndexQueue", json);
    }

}
