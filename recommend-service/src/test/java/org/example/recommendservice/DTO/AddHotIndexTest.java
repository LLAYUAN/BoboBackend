//package org.example.recommendservice.DTO;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class AddHotIndexTest {
//
//    @Test
//    void testNoArgsConstructor() {
//        AddHotIndex addHotIndex = new AddHotIndex();
//        assertNotNull(addHotIndex);
//    }
//
//    @Test
//    void testAllArgsConstructor() {
//        AddHotIndex addHotIndex = new AddHotIndex(1, 100, 50, 25, 10, 5, 2, 500);
//        assertEquals(1, addHotIndex.getRoomId());
//        assertEquals(100, addHotIndex.getViewCount());
//        assertEquals(50, addHotIndex.getLikeCount());
//        assertEquals(25, addHotIndex.getShareCount());
//        assertEquals(10, addHotIndex.getConsumptionCount());
//        assertEquals(5, addHotIndex.getMessageCount());
//        assertEquals(2, addHotIndex.getNewFollowerCount());
//        assertEquals(500, addHotIndex.getSumViewTime());
//    }
//
//    @Test
//    void testSettersAndGetters() {
//        AddHotIndex addHotIndex = new AddHotIndex();
//        addHotIndex.setRoomId(1);
//        addHotIndex.setViewCount(100);
//        addHotIndex.setLikeCount(50);
//        addHotIndex.setShareCount(25);
//        addHotIndex.setConsumptionCount(10);
//        addHotIndex.setMessageCount(5);
//        addHotIndex.setNewFollowerCount(2);
//        addHotIndex.setSumViewTime(500);
//
//        assertEquals(1, addHotIndex.getRoomId());
//        assertEquals(100, addHotIndex.getViewCount());
//        assertEquals(50, addHotIndex.getLikeCount());
//        assertEquals(25, addHotIndex.getShareCount());
//        assertEquals(10, addHotIndex.getConsumptionCount());
//        assertEquals(5, addHotIndex.getMessageCount());
//        assertEquals(2, addHotIndex.getNewFollowerCount());
//        assertEquals(500, addHotIndex.getSumViewTime());
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        AddHotIndex addHotIndex1 = new AddHotIndex(1, 100, 50, 25, 10, 5, 2, 500);
//        AddHotIndex addHotIndex2 = new AddHotIndex(1, 100, 50, 25, 10, 5, 2, 500);
//
//        assertEquals(addHotIndex1, addHotIndex2);
//        assertEquals(addHotIndex1.hashCode(), addHotIndex2.hashCode());
//    }
//
//    @Test
//    void testToString() {
//        AddHotIndex addHotIndex = new AddHotIndex(1, 100, 50, 25, 10, 5, 2, 500);
//        String expectedString = "AddHotIndex(roomId=1, viewCount=100, likeCount=50, shareCount=25, consumptionCount=10, messageCount=5, newFollowerCount=2, sumViewTime=500)";
//        assertEquals(expectedString, addHotIndex.toString());
//    }
//}
