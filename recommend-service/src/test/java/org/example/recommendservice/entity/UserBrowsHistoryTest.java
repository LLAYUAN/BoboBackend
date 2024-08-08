//package org.example.recommendservice.entity;
//
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class UserBrowsHistoryTest {
//
//    @Test
//    public void testNoArgsConstructor() {
//        UserBrowsHistory userBrowsHistory = new UserBrowsHistory();
//        assertNotNull(userBrowsHistory);
//    }
//
//    @Test
//    public void testAllArgsConstructor() {
//        List<BrowsingRecord> browsingHistory = new ArrayList<>();
//        LocalDateTime startTime = LocalDateTime.now();
//        browsingHistory.add(new BrowsingRecord(1, 1, 1, 1, 1, 1, startTime, 60));
//        browsingHistory.add(new BrowsingRecord(2, 0, 0, 0, 0, 0, startTime.plusHours(1), 30));
//
//        UserBrowsHistory userBrowsHistory = new UserBrowsHistory("user123", browsingHistory);
//
//        assertEquals("user123", userBrowsHistory.getUserId());
//        assertEquals(2, userBrowsHistory.getBrowsingHistory().size());
//        assertEquals(1, userBrowsHistory.getBrowsingHistory().get(0).getRoomId());
//        assertEquals(2, userBrowsHistory.getBrowsingHistory().get(1).getRoomId());
//    }
//
//    @Test
//    public void testSettersAndGetters() {
//        UserBrowsHistory userBrowsHistory = new UserBrowsHistory();
//
//        List<BrowsingRecord> browsingHistory = new ArrayList<>();
//        LocalDateTime startTime = LocalDateTime.now();
//        browsingHistory.add(new BrowsingRecord(1, 1, 1, 1, 1, 1, startTime, 60));
//        browsingHistory.add(new BrowsingRecord(2, 0, 0, 0, 0, 0, startTime.plusHours(1), 30));
//
//        userBrowsHistory.setUserId("user456");
//        userBrowsHistory.setBrowsingHistory(browsingHistory);
//
//        assertEquals("user456", userBrowsHistory.getUserId());
//        assertEquals(2, userBrowsHistory.getBrowsingHistory().size());
//        assertEquals(1, userBrowsHistory.getBrowsingHistory().get(0).getRoomId());
//        assertEquals(2, userBrowsHistory.getBrowsingHistory().get(1).getRoomId());
//    }
//}
