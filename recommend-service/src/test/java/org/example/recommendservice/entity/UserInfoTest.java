//package org.example.recommendservice.entity;
//
//import org.junit.jupiter.api.Test;
//
//import java.sql.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class UserInfoTest {
//    RoomInfo roomInfo = new RoomInfo();
//    @Test
//    public void testNoArgsConstructor() {
//        UserInfo userInfo = new UserInfo();
//        assertNotNull(userInfo);
//    }
//    @Test
//    public void testAllArgsConstructor() {
//        Date birthday = new Date(System.currentTimeMillis());
//        UserInfo userInfo = new UserInfo("john@example.com", "John Doe", "Self Intro", birthday);
//
//        assertEquals("john@example.com", userInfo.getEmail());
//        assertEquals("John Doe", userInfo.getNickname());
//        assertEquals("Self Intro", userInfo.getSelfIntro());
//        assertEquals(birthday, userInfo.getBirthday());
//        assertEquals(false, userInfo.getIsAdmin());
//    }
//    @Test
//    public void testPartArgsConstructor() {
//        Date birthday = new Date(System.currentTimeMillis());
//        UserInfo userInfo = new UserInfo(1, "password", "john@example.com", "John Doe", "Self Intro", birthday, true, "avatar.jpg", roomInfo);
//
//        assertEquals(1, userInfo.getUserID());
//        assertEquals("password", userInfo.getPassword());
//        assertEquals("john@example.com", userInfo.getEmail());
//        assertEquals("John Doe", userInfo.getNickname());
//        assertEquals("Self Intro", userInfo.getSelfIntro());
//        assertEquals(birthday, userInfo.getBirthday());
//        assertEquals(true, userInfo.getIsAdmin());
//        assertEquals("avatar.jpg", userInfo.getAvatarUrl());
//        assertEquals(roomInfo, userInfo.getRoomInfo());
//    }
//    @Test
//    public void testSettersAndGetters() {
//        UserInfo userInfo = new UserInfo();
//
//        userInfo.setUserID(2);
//        userInfo.setPassword("newpassword");
//        userInfo.setEmail("jane@example.com");
//        userInfo.setNickname("Jane Doe");
//        userInfo.setSelfIntro("Hello, I'm Jane.");
//        userInfo.setBirthday(new Date(System.currentTimeMillis()));
//        userInfo.setIsAdmin(false);
//        userInfo.setAvatarUrl("new_avatar.jpg");
//
//        assertEquals(2, userInfo.getUserID());
//        assertEquals("newpassword", userInfo.getPassword());
//        assertEquals("jane@example.com", userInfo.getEmail());
//        assertEquals("Jane Doe", userInfo.getNickname());
//        assertEquals("Hello, I'm Jane.", userInfo.getSelfIntro());
//        assertNotNull(userInfo.getBirthday());
//        assertEquals(false, userInfo.getIsAdmin());
//        assertEquals("new_avatar.jpg", userInfo.getAvatarUrl());
//    }
//
//    @Test
//    public void testConstructorWithEmailPassword() {
//        UserInfo userInfo = new UserInfo("jane@example.com", "password123");
//
//        assertEquals("jane@example.com", userInfo.getEmail());
//        assertEquals("password123", userInfo.getPassword());
//        assertEquals(false, userInfo.getIsAdmin());
//    }
//}
