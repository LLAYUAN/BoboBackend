package org.example.messageservice.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    private ChatMessage message;

    @BeforeEach
    void setUp() {
        // 初始化ChatMessage对象，为测试做准备
        message = new ChatMessage();
        message.setId(String.valueOf(1));
        message.setType(ChatMessage.MessageType.valueOf("JOIN"));
        message.setContent("Hello, this is a test message.");
        message.setSender("testUser");
        message.setRoomID(101010);
        message.setUserID(202);
        message.setTimestamp(Instant.ofEpochSecond(123456));
    }

    @AfterEach
    void tearDown() {
        // 清理工作，如果有的话
    }

    // 测试获取ID
    @Test
    void getId() {
        assertEquals(String.valueOf(1), message.getId());
    }

    // 测试获取类型
    @Test
    void getType() {
        assertEquals(ChatMessage.MessageType.valueOf("JOIN"), message.getType());
    }

    // 测试获取内容
    @Test
    void getContent() {
        assertEquals("Hello, this is a test message.", message.getContent());
    }

    // 测试获取发送者
    @Test
    void getSender() {
        assertEquals("testUser", message.getSender());
    }

    // 测试获取房间ID
    @Test
    void getRoomID() {
        assertEquals(101010, message.getRoomID());
    }

    // 测试获取用户ID
    @Test
    void getUserID() {
        assertEquals(202, message.getUserID());
    }

    // 测试获取时间戳
    @Test
    void getTimestamp() {
        assertEquals(Instant.ofEpochSecond(123456), message.getTimestamp());
    }

    // 测试设置ID
    @Test
    void setId() {
        message.setId(String.valueOf(2));
        assertEquals(String.valueOf(2), message.getId());
    }

    // 测试设置类型
    @Test
    void setType() {
        message.setType(ChatMessage.MessageType.valueOf("CHAT"));
        assertEquals(ChatMessage.MessageType.valueOf("CHAT"), message.getType());
    }

    // 测试设置内容
    @Test
    void setContent() {
        message.setContent("Updated content.");
        assertEquals("Updated content.", message.getContent());
    }

    // 测试设置发送者
    @Test
    void setSender() {
        message.setSender("newUser");
        assertEquals("newUser", message.getSender());
    }

    // 测试设置房间ID
    @Test
    void setRoomID() {
        message.setRoomID(202020);
        assertEquals(202020, message.getRoomID());
    }

    // 测试设置用户ID
    @Test
    void setUserID() {
        message.setUserID(303);
        assertEquals(303, message.getUserID());
    }

    // 测试设置时间戳
    @Test
    void setTimestamp() {
        long newTimestamp = System.currentTimeMillis() + 1000; // 假设1秒后的时间戳
        message.setTimestamp(Instant.ofEpochSecond(newTimestamp));
        assertEquals(Instant.ofEpochSecond(newTimestamp), message.getTimestamp());
    }

    // 测试equals方法
    @Test
    void testEquals() {
        ChatMessage anotherMessage = new ChatMessage();
        anotherMessage.setId(String.valueOf(1)); // 假设ID相同
        anotherMessage.setType(ChatMessage.MessageType.valueOf("JOIN"));
        anotherMessage.setContent("Hello, this is a test message.");
        anotherMessage.setSender("testUser");
        anotherMessage.setRoomID(101010);
        anotherMessage.setUserID(202);
        anotherMessage.setTimestamp(Instant.ofEpochSecond(123456));
        assertEquals(message, anotherMessage);
    }

    // 测试canEqual方法
    @Test
    void canEqual() {
        assertTrue(message.canEqual(new ChatMessage()));
    }


    // 测试toString方法
    @Test
    void testToString() {
        String expected = "ChatMessage(id=1, type=JOIN, content=Hello, this is a test message., sender=testUser, roomID=101010, userID=202, timestamp=";
        assertTrue(message.toString().contains(expected));
    }
}