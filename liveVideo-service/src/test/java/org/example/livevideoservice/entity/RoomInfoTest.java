package org.example.livevideoservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomInfoTest {

    private RoomInfo roomInfoUnderTest;

    @BeforeEach
    void setUp() {
        roomInfoUnderTest = new RoomInfo(0, "roomName", "description", false, "coverUrl", false, false, false);
    }

    @Test
    void testConstructorWithArguments() {
        assertNotNull(roomInfoUnderTest);
        assertEquals(0, roomInfoUnderTest.getRoomID());
        assertEquals("roomName", roomInfoUnderTest.getRoomName());
        assertEquals("description", roomInfoUnderTest.getDescription());
        assertFalse(roomInfoUnderTest.getStatus());
        assertEquals("coverUrl", roomInfoUnderTest.getCoverUrl());
        assertFalse(roomInfoUnderTest.getStudy());
        assertFalse(roomInfoUnderTest.getEntertain());
        assertFalse(roomInfoUnderTest.getOther());
    }

    @Test
    void testConstructorWithoutArguments() {
        RoomInfo defaultRoomInfo = new RoomInfo("defaultRoomName", "defaultDescription", "defaultCoverUrl");
        assertNotNull(defaultRoomInfo);
        assertEquals("defaultRoomName", defaultRoomInfo.getRoomName());
        assertEquals("defaultDescription", defaultRoomInfo.getDescription());
        assertEquals("defaultCoverUrl", defaultRoomInfo.getCoverUrl());
        assertTrue(defaultRoomInfo.getStatus());
        assertFalse(defaultRoomInfo.getStudy());
        assertFalse(defaultRoomInfo.getEntertain());
        assertFalse(defaultRoomInfo.getOther());
    }

    @Test
    void testSettersAndGetters() {
        roomInfoUnderTest.setRoomID(1);
        assertEquals(1, roomInfoUnderTest.getRoomID());

        roomInfoUnderTest.setRoomName("newRoomName");
        assertEquals("newRoomName", roomInfoUnderTest.getRoomName());

        roomInfoUnderTest.setDescription("newDescription");
        assertEquals("newDescription", roomInfoUnderTest.getDescription());

        roomInfoUnderTest.setStatus(true);
        assertTrue(roomInfoUnderTest.getStatus());

        roomInfoUnderTest.setCoverUrl("newCoverUrl");
        assertEquals("newCoverUrl", roomInfoUnderTest.getCoverUrl());

        roomInfoUnderTest.setStudy(true);
        assertTrue(roomInfoUnderTest.getStudy());

        roomInfoUnderTest.setEntertain(true);
        assertTrue(roomInfoUnderTest.getEntertain());

        roomInfoUnderTest.setOther(true);
        assertTrue(roomInfoUnderTest.getOther());
    }

    @Test
    void testDefaultConstructor() {
        RoomInfo emptyRoomInfo = new RoomInfo();
        assertNotNull(emptyRoomInfo);
    }

    @Test
    void testAllArgsConstructor() {
        RoomInfo fullRoomInfo = new RoomInfo(1, "Full Room", "Full Description", true, "fullCoverUrl", true, true, true);
        assertNotNull(fullRoomInfo);
        assertEquals(1, fullRoomInfo.getRoomID());
        assertEquals("Full Room", fullRoomInfo.getRoomName());
        assertEquals("Full Description", fullRoomInfo.getDescription());
        assertTrue(fullRoomInfo.getStatus());
        assertEquals("fullCoverUrl", fullRoomInfo.getCoverUrl());
        assertTrue(fullRoomInfo.getStudy());
        assertTrue(fullRoomInfo.getEntertain());
        assertTrue(fullRoomInfo.getOther());
    }

    @Test
    void testToString() {
        String expectedString = "RoomInfo(roomID=0, roomName=roomName, description=description, status=false, coverUrl=coverUrl, study=false, entertain=false, other=false)";
        assertEquals(expectedString, roomInfoUnderTest.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        RoomInfo anotherRoomInfo = new RoomInfo(0, "roomName", "description", false, "coverUrl", false, false, false);
        assertEquals(roomInfoUnderTest, anotherRoomInfo);
        assertEquals(roomInfoUnderTest.hashCode(), anotherRoomInfo.hashCode());

        anotherRoomInfo.setRoomID(1);
        assertNotEquals(roomInfoUnderTest, anotherRoomInfo);
    }

    @Test
    void testIsActiveStatus() {
        roomInfoUnderTest.setStatus(true);
        assertTrue(roomInfoUnderTest.getStatus());

        roomInfoUnderTest.setStatus(false);
        assertFalse(roomInfoUnderTest.getStatus());
    }
}
