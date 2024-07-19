package org.example.messageservice.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.time.Instant;

//自动补充get set
@Data
public class ChatMessage {
    @Id
    private String id;
    private MessageType type;
    private String content;
    private String sender;
    private Integer roomID;
    private Integer userID;

    @Indexed
    private Instant timestamp; // Timestamp for message

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        GIFT
    }

}
