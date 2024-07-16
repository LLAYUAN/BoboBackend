package org.example.recommendservice.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoomInfoTest {

    @Test
    public void testNoArgsConstructor() {
        RoomInfo roomInfo = new RoomInfo();
        assertNotNull(roomInfo);
    }

    @Test
    public void testAllArgsConstructor() {
        RoomInfo roomInfo = new RoomInfo("RoomName", "Description of the room", "http://example.com/cover.jpg");
        assertEquals("RoomName", roomInfo.getRoomName());
        assertEquals("Description of the room", roomInfo.getDescription());
        assertEquals("http://example.com/cover.jpg", roomInfo.getCoverUrl());
        assertEquals(true, roomInfo.getStatus());
        assertEquals(false, roomInfo.getStudy());
        assertEquals(false, roomInfo.getEntertain());
        assertEquals(false, roomInfo.getOther());
    }

    @Test
    public void testSettersAndGetters() {
        RoomInfo roomInfo = new RoomInfo();

        roomInfo.setRoomName("RoomName");
        roomInfo.setDescription("Description of the room");
        roomInfo.setCoverUrl("http://example.com/cover.jpg");

        assertEquals("RoomName", roomInfo.getRoomName());
        assertEquals("Description of the room", roomInfo.getDescription());
        assertEquals("http://example.com/cover.jpg", roomInfo.getCoverUrl());
    }
}
