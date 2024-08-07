//package org.example.livevideoservice.entity;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserActivityTest {
//
//    private UserActivity userActivityUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        userActivityUnderTest = new UserActivity("id", "userId", "roomId",
//                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
//                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
//    }
//
//    @Test
//    void testConstructorWithArguments() {
//        assertNotNull(userActivityUnderTest);
//        assertEquals("id", userActivityUnderTest.getId());
//        assertEquals("userId", userActivityUnderTest.getUserId());
//        assertEquals("roomId", userActivityUnderTest.getRoomId());
//        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0, 0), userActivityUnderTest.getEnterTime());
//        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0, 0), userActivityUnderTest.getExitTime());
//    }
//
//    @Test
//    void testNoArgsConstructor() {
//        UserActivity defaultUserActivity = new UserActivity();
//        assertNotNull(defaultUserActivity);
//    }
//
//    @Test
//    void testSettersAndGetters() {
//        userActivityUnderTest.setId("newId");
//        assertEquals("newId", userActivityUnderTest.getId());
//
//        userActivityUnderTest.setUserId("newUserId");
//        assertEquals("newUserId", userActivityUnderTest.getUserId());
//
//        userActivityUnderTest.setRoomId("newRoomId");
//        assertEquals("newRoomId", userActivityUnderTest.getRoomId());
//
//        LocalDateTime newEnterTime = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
//        userActivityUnderTest.setEnterTime(newEnterTime);
//        assertEquals(newEnterTime, userActivityUnderTest.getEnterTime());
//
//        LocalDateTime newExitTime = LocalDateTime.of(2021, 1, 1, 1, 0, 0);
//        userActivityUnderTest.setExitTime(newExitTime);
//        assertEquals(newExitTime, userActivityUnderTest.getExitTime());
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        UserActivity anotherUserActivity = new UserActivity("id", "userId", "roomId",
//                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
//                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
//        assertEquals(userActivityUnderTest, anotherUserActivity);
//        assertEquals(userActivityUnderTest.hashCode(), anotherUserActivity.hashCode());
//
//        anotherUserActivity.setId("differentId");
//        assertNotEquals(userActivityUnderTest, anotherUserActivity);
//    }
//
//    @Test
//    void testToString() {
//        String expectedString = "UserActivity(id=id, userId=userId, roomId=roomId, enterTime=2020-01-01T00:00, exitTime=2020-01-01T00:00)";
//        assertEquals(expectedString, userActivityUnderTest.toString());
//    }
//}
