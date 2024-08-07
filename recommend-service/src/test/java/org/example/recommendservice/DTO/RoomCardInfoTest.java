//package org.example.recommendservice.DTO;
//
//import org.example.recommendservice.entity.RoomInfo;
//import org.example.recommendservice.entity.UserInfo;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RoomCardInfoTest {
//
//    @Test
//    void testNoArgsConstructor() {
//        RoomCardInfo roomCardInfo = new RoomCardInfo();
//        assertNotNull(roomCardInfo);
//    }
//
//    @Test
//    void testAllArgsConstructor() {
//        List<Boolean> tags = Arrays.asList(true, false, true);
//        RoomCardInfo roomCardInfo = new RoomCardInfo(1, 1001, "Test Room", "Description", "coverUrl", "User Name", "User Description", "avatarUrl", tags, 10);
//        assertEquals(1, roomCardInfo.getId());
//        assertEquals(1001, roomCardInfo.getUserID());
//        assertEquals("Test Room", roomCardInfo.getRoomName());
//        assertEquals("Description", roomCardInfo.getDescription());
//        assertEquals("coverUrl", roomCardInfo.getCoverUrl());
//        assertEquals("User Name", roomCardInfo.getUserName());
//        assertEquals("User Description", roomCardInfo.getUserDescription());
//        assertEquals("avatarUrl", roomCardInfo.getAvatarUrl());
//        assertEquals(tags, roomCardInfo.getTags());
//        assertEquals(10, roomCardInfo.getHotIndex());
//    }
//
//    @Test
//    void testSettersAndGetters() {
//        RoomCardInfo roomCardInfo = new RoomCardInfo();
//        List<Boolean> tags = Arrays.asList(true, false, true);
//
//        roomCardInfo.setId(1);
//        roomCardInfo.setUserID(1001);
//        roomCardInfo.setRoomName("Test Room");
//        roomCardInfo.setDescription("Description");
//        roomCardInfo.setCoverUrl("coverUrl");
//        roomCardInfo.setUserName("User Name");
//        roomCardInfo.setUserDescription("User Description");
//        roomCardInfo.setAvatarUrl("avatarUrl");
//        roomCardInfo.setTags(tags);
//        roomCardInfo.setHotIndex(10);
//
//        assertEquals(1, roomCardInfo.getId());
//        assertEquals(1001, roomCardInfo.getUserID());
//        assertEquals("Test Room", roomCardInfo.getRoomName());
//        assertEquals("Description", roomCardInfo.getDescription());
//        assertEquals("coverUrl", roomCardInfo.getCoverUrl());
//        assertEquals("User Name", roomCardInfo.getUserName());
//        assertEquals("User Description", roomCardInfo.getUserDescription());
//        assertEquals("avatarUrl", roomCardInfo.getAvatarUrl());
//        assertEquals(tags, roomCardInfo.getTags());
//        assertEquals(10, roomCardInfo.getHotIndex());
//    }
//
//    @Test
//    void testConstructorWithRoomInfo() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserID(1001);
//        userInfo.setNickname("User Name");
//        userInfo.setSelfIntro("User Description");
//        userInfo.setAvatarUrl("avatarUrl");
//
//        RoomInfo roomInfo = new RoomInfo();
//        roomInfo.setRoomID(1);
//        roomInfo.setUserInfo(userInfo);
//        roomInfo.setRoomName("Test Room");
//        roomInfo.setDescription("Description");
//        roomInfo.setCoverUrl("coverUrl");
//        roomInfo.setStudy(true);
//        roomInfo.setEntertain(false);
//        roomInfo.setOther(true);
//
//        RoomCardInfo roomCardInfo = new RoomCardInfo(roomInfo);
//
//        assertEquals(1, roomCardInfo.getId());
//        assertEquals(1001, roomCardInfo.getUserID());
//        assertEquals("Test Room", roomCardInfo.getRoomName());
//        assertEquals("Description", roomCardInfo.getDescription());
//        assertEquals("coverUrl", roomCardInfo.getCoverUrl());
//        assertEquals("User Name", roomCardInfo.getUserName());
//        assertEquals("User Description", roomCardInfo.getUserDescription());
//        assertEquals("avatarUrl", roomCardInfo.getAvatarUrl());
//        assertEquals(Arrays.asList(true, false, true), roomCardInfo.getTags());
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        List<Boolean> tags1 = Arrays.asList(true, false, true);
//        List<Boolean> tags2 = Arrays.asList(true, false, true);
//        RoomCardInfo roomCardInfo1 = new RoomCardInfo(1, 1001, "Test Room", "Description", "coverUrl", "User Name", "User Description", "avatarUrl", tags1, 10);
//        RoomCardInfo roomCardInfo2 = new RoomCardInfo(1, 1001, "Test Room", "Description", "coverUrl", "User Name", "User Description", "avatarUrl", tags2, 10);
//
//        assertEquals(roomCardInfo1, roomCardInfo2);
//        assertEquals(roomCardInfo1.hashCode(), roomCardInfo2.hashCode());
//    }
//
//    @Test
//    void testToString() {
//        List<Boolean> tags = Arrays.asList(true, false, true);
//        RoomCardInfo roomCardInfo = new RoomCardInfo(1, 1001, "Test Room", "Description", "coverUrl", "User Name", "User Description", "avatarUrl", tags, 10);
//        String expectedString = "RoomCardInfo(id=1, userID=1001, roomName=Test Room, description=Description, coverUrl=coverUrl, userName=User Name, userDescription=User Description, avatarUrl=avatarUrl, tags=[true, false, true], hotIndex=10)";
//        assertEquals(expectedString, roomCardInfo.toString());
//    }
//}
