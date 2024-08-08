//package org.example.userservice.entity;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RoomInfoTest {
//
//    private RoomInfo roomInfoUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        roomInfoUnderTest = new RoomInfo("roomName", "description", "coverUrl");
//    }
//
//    @Test
//    void testConstructorWithParameters() {
//        // Test constructor with parameters
//        assertEquals("roomName", roomInfoUnderTest.getRoomName());
//        assertEquals("description", roomInfoUnderTest.getDescription());
//        assertEquals("coverUrl", roomInfoUnderTest.getCoverUrl());
//        assertEquals(false, roomInfoUnderTest.getStatus());
//        assertEquals(false, roomInfoUnderTest.getStudy());
//        assertEquals(false, roomInfoUnderTest.getEntertain());
//        assertEquals(false, roomInfoUnderTest.getOther());
//        assertNull(roomInfoUnderTest.getRoomID());
//        assertNull(roomInfoUnderTest.getStartTime());
//        assertNull(roomInfoUnderTest.getEndTime());
//        assertNull(roomInfoUnderTest.getUserInfo());
//    }
//
//    @Test
//    void testConstructorWithIdAndFlags() {
//        // Test constructor with ID and flags
//        RoomInfo roomInfo = new RoomInfo(1, "roomName", true, false, true, "description", "coverUrl");
//        assertEquals(1, roomInfo.getRoomID());
//        assertEquals("roomName", roomInfo.getRoomName());
//        assertEquals("description", roomInfo.getDescription());
//        assertEquals(false, roomInfo.getStatus());
//        assertEquals(true, roomInfo.getStudy());
//        assertEquals(false, roomInfo.getEntertain());
//        assertEquals(true, roomInfo.getOther());
//        assertNull(roomInfo.getStartTime());
//        assertNull(roomInfo.getEndTime());
////        assertNull(roomInfo.getUserInfo());
//    }
//
//    @Test
//    void testToString() {
//        // Test toString method
//        String expectedToString = "RoomInfo(roomID=null, roomName=roomName, description=description, status=false, coverUrl=coverUrl, study=false, entertain=false, other=false, startTime=null, endTime=null, userInfo=null)";
//        assertEquals(expectedToString, roomInfoUnderTest.toString());
//    }
//
//    @Test
//    void testNoArgsConstructor() {
//        // Test no-args constructor
//        RoomInfo emptyRoomInfo = new RoomInfo();
//        assertNull(emptyRoomInfo.getRoomID());
//        assertNull(emptyRoomInfo.getRoomName());
//        assertNull(emptyRoomInfo.getDescription());
//        assertNull(emptyRoomInfo.getStatus());
//        assertNull(emptyRoomInfo.getCoverUrl());
//        assertNull(emptyRoomInfo.getStudy());
//        assertNull(emptyRoomInfo.getEntertain());
//        assertNull(emptyRoomInfo.getOther());
//        assertNull(emptyRoomInfo.getStartTime());
//        assertNull(emptyRoomInfo.getEndTime());
//        assertNull(emptyRoomInfo.getUserInfo());
//    }
//
//    @Test
//    void testHashCode() {
//        // Test hashCode method
//        RoomInfo sameRoomInfo = new RoomInfo("roomName", "description", "coverUrl");
//        assertEquals(sameRoomInfo.hashCode(), roomInfoUnderTest.hashCode());
//
//        RoomInfo differentRoomInfo = new RoomInfo("differentName", "differentDescription", "differentCoverUrl");
//        assertNotEquals(differentRoomInfo.hashCode(), roomInfoUnderTest.hashCode());
//    }
//}
