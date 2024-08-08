//package org.example.userservice.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//class CompleteUserTest {
//
//    private CompleteUser completeUserUnderTest;
//    private CompleteUser actualUserUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        completeUserUnderTest = new CompleteUser();
//        actualUserUnderTest = new CompleteUser(1, "testUsername", 1,
//                new ArrayList<>(Arrays.asList("follower1", "follower2")),
//                new ArrayList<>(Arrays.asList("subscription1", "subscription2")));
//    }
//
//    @Test
//    void testAllArgsConstructor() {
//        List<String> followers = new ArrayList<>(Arrays.asList("follower1", "follower2"));
//        List<String> subscriptions = new ArrayList<>(Arrays.asList("subscription1", "subscription2"));
//
//        CompleteUser user = new CompleteUser(1, "username", 1, followers, subscriptions);
//
//        assertEquals(1, user.getId());
//        assertEquals("username", user.getUsername());
//        assertEquals(1, user.getStatus());
//        assertEquals(followers, user.getFollowers());
//        assertEquals(subscriptions, user.getSubscriptions());
//    }
//
//    @Test
//    void testNoArgsConstructor() {
//        CompleteUser user = new CompleteUser();
//
//        assertNull(user.getId());
//        assertNull(user.getUsername());
//        assertNull(user.getStatus());
//        assertNull(user.getFollowers());
//        assertNull(user.getSubscriptions());
//    }
//
//    @Test
//    void testSetterAndGetters() {
//        completeUserUnderTest.setId(1);
//        completeUserUnderTest.setUsername("testUsername");
//        completeUserUnderTest.setStatus(1);
//
//        List<String> followers = new ArrayList<>(Arrays.asList("follower1", "follower2"));
//        completeUserUnderTest.setFollowers(followers);
//
//        List<String> subscriptions = new ArrayList<>(Arrays.asList("subscription1", "subscription2"));
//        completeUserUnderTest.setSubscriptions(subscriptions);
//
//        assertEquals(1, completeUserUnderTest.getId());
//        assertEquals("testUsername", completeUserUnderTest.getUsername());
//        assertEquals(1, completeUserUnderTest.getStatus());
//        assertEquals(followers, completeUserUnderTest.getFollowers());
//        assertEquals(subscriptions, completeUserUnderTest.getSubscriptions());
//    }
//
//    @Test
//    void testToString() {
//        completeUserUnderTest.setId(1);
//        completeUserUnderTest.setUsername("testUsername");
//        completeUserUnderTest.setStatus(1);
//
//        List<String> followers = new ArrayList<>(Arrays.asList("follower1", "follower2"));
//        completeUserUnderTest.setFollowers(followers);
//
//        List<String> subscriptions = new ArrayList<>(Arrays.asList("subscription1", "subscription2"));
//        completeUserUnderTest.setSubscriptions(subscriptions);
//
//        String expectedToString = "CompleteUser(id=1, username=testUsername, status=1, followers=[follower1, follower2], subscriptions=[subscription1, subscription2])";
//        assertEquals(expectedToString, completeUserUnderTest.toString());
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        CompleteUser sameUser = new CompleteUser(1, "testUsername", 1,
//                new ArrayList<>(Arrays.asList("follower1", "follower2")),
//                new ArrayList<>(Arrays.asList("subscription1", "subscription2")));
//
//        assertEquals(sameUser, actualUserUnderTest);
//        assertEquals(sameUser.hashCode(), actualUserUnderTest.hashCode());
//
//        CompleteUser differentUser = new CompleteUser(2, "differentUsername", 2,
//                new ArrayList<>(Arrays.asList("differentFollower1", "differentFollower2")),
//                new ArrayList<>(Arrays.asList("differentSubscription1", "differentSubscription2")));
//
//        assertNotEquals(differentUser, actualUserUnderTest);
//    }
//}
