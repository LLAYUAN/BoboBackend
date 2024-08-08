//package org.example.recommendservice.entity;
//
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class BrowsingRecordTest {
//
//    @Test
//    public void testNoArgsConstructor() {
//        BrowsingRecord record = new BrowsingRecord();
//        assertNotNull(record);
//    }
//
//    @Test
//    public void testAllArgsConstructor() {
//        LocalDateTime startTime = LocalDateTime.now();
//        BrowsingRecord record = new BrowsingRecord(1, 10, 5, 2, 50, 1, startTime, 3600);
//
//        assertEquals(1, record.getRoomId());
//        assertEquals(10, record.getLikeCount());
//        assertEquals(5, record.getShareCount());
//        assertEquals(2, record.getConsumptionCount());
//        assertEquals(50, record.getMessageCount());
//        assertEquals(1, record.getFollowStatus());
//        assertEquals(startTime, record.getStartTime());
//        assertEquals(3600, record.getWatchDuration());
//    }
//
//    @Test
//    public void testSettersAndGetters() {
//        BrowsingRecord record = new BrowsingRecord();
//        LocalDateTime startTime = LocalDateTime.now();
//
//        record.setRoomId(1);
//        record.setLikeCount(10);
//        record.setShareCount(5);
//        record.setConsumptionCount(2);
//        record.setMessageCount(50);
//        record.setFollowStatus(1);
//        record.setStartTime(startTime);
//        record.setWatchDuration(3600);
//
//        assertEquals(1, record.getRoomId());
//        assertEquals(10, record.getLikeCount());
//        assertEquals(5, record.getShareCount());
//        assertEquals(2, record.getConsumptionCount());
//        assertEquals(50, record.getMessageCount());
//        assertEquals(1, record.getFollowStatus());
//        assertEquals(startTime, record.getStartTime());
//        assertEquals(3600, record.getWatchDuration());
//    }
//
//    @Test
//    public void testToString() {
//        LocalDateTime startTime = LocalDateTime.now();
//        BrowsingRecord record = new BrowsingRecord(1, 10, 5, 2, 50, 1, startTime, 3600);
//
//        String expectedString = "BrowsingRecord(roomId=1, likeCount=10, shareCount=5, consumptionCount=2, messageCount=50, followStatus=1, startTime=" + startTime + ", watchDuration=3600)";
//        assertEquals(expectedString, record.toString());
//    }
//
//    @Test
//    public void testEqualsAndHashCode() {
//        LocalDateTime startTime = LocalDateTime.now();
//        BrowsingRecord record1 = new BrowsingRecord(1, 10, 5, 2, 50, 1, startTime, 3600);
//        BrowsingRecord record2 = new BrowsingRecord(1, 10, 5, 2, 50, 1, startTime, 3600);
//        BrowsingRecord record3 = new BrowsingRecord(2, 20, 15, 12, 150, -1, startTime, 7200);
//
//        assertEquals(record1, record2);
//        assertEquals(record1.hashCode(), record2.hashCode());
//
//        assertNotEquals(record1, record3);
//        assertNotEquals(record1.hashCode(), record3.hashCode());
//    }
//}
