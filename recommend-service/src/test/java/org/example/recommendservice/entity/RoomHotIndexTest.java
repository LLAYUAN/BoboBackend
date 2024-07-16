package org.example.recommendservice.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomHotIndexTest {

    @Test
    public void testNoArgsConstructor() {
        RoomHotIndex roomHotIndex = new RoomHotIndex();
        assertNotNull(roomHotIndex);
    }

    @Test
    public void testAllArgsConstructor() {
        RoomHotIndex roomHotIndex = new RoomHotIndex(1, 60, 100, 50, 20, 5, 200, 10, 3600);

        assertEquals(1, roomHotIndex.getRoomId());
        assertEquals(60, roomHotIndex.getDuration());
        assertEquals(100, roomHotIndex.getViewCount());
        assertEquals(50, roomHotIndex.getLikeCount());
        assertEquals(20, roomHotIndex.getShareCount());
        assertEquals(5, roomHotIndex.getConsumptionCount());
        assertEquals(200, roomHotIndex.getMessageCount());
        assertEquals(10, roomHotIndex.getNewFollowerCount());
        assertEquals(3600, roomHotIndex.getSumViewTime());
    }

    @Test
    public void testSettersAndGetters() {
        RoomHotIndex roomHotIndex = new RoomHotIndex();

        roomHotIndex.setRoomId(2);
        roomHotIndex.setDuration(45);
        roomHotIndex.setViewCount(80);
        roomHotIndex.setLikeCount(30);
        roomHotIndex.setShareCount(15);
        roomHotIndex.setConsumptionCount(10);
        roomHotIndex.setMessageCount(150);
        roomHotIndex.setNewFollowerCount(5);
        roomHotIndex.setSumViewTime(7200);

        assertEquals(2, roomHotIndex.getRoomId());
        assertEquals(45, roomHotIndex.getDuration());
        assertEquals(80, roomHotIndex.getViewCount());
        assertEquals(30, roomHotIndex.getLikeCount());
        assertEquals(15, roomHotIndex.getShareCount());
        assertEquals(10, roomHotIndex.getConsumptionCount());
        assertEquals(150, roomHotIndex.getMessageCount());
        assertEquals(5, roomHotIndex.getNewFollowerCount());
        assertEquals(7200, roomHotIndex.getSumViewTime());
    }

    @Test
    public void testToString() {
        RoomHotIndex roomHotIndex = new RoomHotIndex(1, 60, 100, 50, 20, 5, 200, 10, 3600);

        String expectedString = "RoomHotIndex(roomId=1, duration=60, viewCount=100, likeCount=50, shareCount=20, consumptionCount=5, messageCount=200, newFollowerCount=10, sumViewTime=3600)";
        assertEquals(expectedString, roomHotIndex.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        RoomHotIndex roomHotIndex1 = new RoomHotIndex(1, 60, 100, 50, 20, 5, 200, 10, 3600);
        RoomHotIndex roomHotIndex2 = new RoomHotIndex(1, 60, 100, 50, 20, 5, 200, 10, 3600);
        RoomHotIndex roomHotIndex3 = new RoomHotIndex(2, 45, 80, 30, 15, 10, 150, 5, 7200);

        assertEquals(roomHotIndex1, roomHotIndex2);
        assertEquals(roomHotIndex1.hashCode(), roomHotIndex2.hashCode());

        assertNotEquals(roomHotIndex1, roomHotIndex3);
        assertNotEquals(roomHotIndex1.hashCode(), roomHotIndex3.hashCode());
    }
}
