//package org.example.recommendservice.entity;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class RoomInfoTest {
//    UserInfo userInfo = new UserInfo();
//
//    @Test
//    public void testNoArgsConstructor() {
//        RoomInfo roomInfo = new RoomInfo();
//        assertNotNull(roomInfo);
//    }
//
//    @Test
//    public void testAllArgsConstructor() {
//        RoomInfo roomInfo = new RoomInfo(
//                1,
//                "RoomName",
//                "Description of the room",
//                true,
//                "http://example.com/cover.jpg",
//                true,
//                true,
//                true,
//                userInfo);
//        roomInfo.setRoomID(1);
//        roomInfo.setUserInfo(userInfo);
//        assertEquals(1, roomInfo.getRoomID());
//        assertEquals("RoomName", roomInfo.getRoomName());
//        assertEquals("Description of the room", roomInfo.getDescription());
//        assertEquals("http://example.com/cover.jpg", roomInfo.getCoverUrl());
//        assertEquals(true, roomInfo.getStatus());
//        assertEquals(true, roomInfo.getStudy());
//        assertEquals(true, roomInfo.getEntertain());
//        assertEquals(true, roomInfo.getOther());
//        assertEquals(userInfo, roomInfo.getUserInfo());
//    }
//
//    @Test
//    public void testPartArgsConstructor() {
//        RoomInfo roomInfo = new RoomInfo("RoomName", "Description of the room", "http://example.com/cover.jpg");
//        assertEquals("RoomName", roomInfo.getRoomName());
//        assertEquals("Description of the room", roomInfo.getDescription());
//        assertEquals("http://example.com/cover.jpg", roomInfo.getCoverUrl());
//        assertEquals(true, roomInfo.getStatus());
//        assertEquals(false, roomInfo.getStudy());
//        assertEquals(false, roomInfo.getEntertain());
//        assertEquals(false, roomInfo.getOther());
//    }
//
//    @Test
//    public void testSettersAndGetters() {
//        RoomInfo roomInfo = new RoomInfo();
//
//        roomInfo.setRoomName("RoomName");
//        roomInfo.setDescription("Description of the room");
//        roomInfo.setCoverUrl("http://example.com/cover.jpg");
//
//        assertEquals("RoomName", roomInfo.getRoomName());
//        assertEquals("Description of the room", roomInfo.getDescription());
//        assertEquals("http://example.com/cover.jpg", roomInfo.getCoverUrl());
//    }
//}
