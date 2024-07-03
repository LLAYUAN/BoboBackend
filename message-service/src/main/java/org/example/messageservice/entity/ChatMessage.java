package org.example.messageservice.entity;

import lombok.Data;

//自动补充get set
@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String timestamp;
    private String roomID;


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

}
