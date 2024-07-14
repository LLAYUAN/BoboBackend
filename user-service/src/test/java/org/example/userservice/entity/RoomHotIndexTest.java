package org.example.userservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomHotIndexTest {

    private RoomHotIndex roomHotIndexUnderTest;

    @BeforeEach
    void setUp() {
        roomHotIndexUnderTest = new RoomHotIndex(1, 10, 20, 30, 40, 50, 60, 70, 80);
    }

    @Test
    void testConstructor() {
        assertEquals(1, roomHotIndexUnderTest.getRoomId());
        assertEquals(10, roomHotIndexUnderTest.getDuration());
        assertEquals(20, roomHotIndexUnderTest.getViewCount());
        assertEquals(30, roomHotIndexUnderTest.getLikeCount());
        assertEquals(40, roomHotIndexUnderTest.getShareCount());
        assertEquals(50, roomHotIndexUnderTest.getConsumptionCount());
        assertEquals(60, roomHotIndexUnderTest.getMessageCount());
        assertEquals(70, roomHotIndexUnderTest.getNewFollowerCount());
        assertEquals(80, roomHotIndexUnderTest.getSumViewTime());
    }

    @Test
    void testSetterAndGetters() {
        roomHotIndexUnderTest.setRoomId(2);
        roomHotIndexUnderTest.setDuration(15);
        roomHotIndexUnderTest.setViewCount(25);
        roomHotIndexUnderTest.setLikeCount(35);
        roomHotIndexUnderTest.setShareCount(45);
        roomHotIndexUnderTest.setConsumptionCount(55);
        roomHotIndexUnderTest.setMessageCount(65);
        roomHotIndexUnderTest.setNewFollowerCount(75);
        roomHotIndexUnderTest.setSumViewTime(85);

        assertEquals(2, roomHotIndexUnderTest.getRoomId());
        assertEquals(15, roomHotIndexUnderTest.getDuration());
        assertEquals(25, roomHotIndexUnderTest.getViewCount());
        assertEquals(35, roomHotIndexUnderTest.getLikeCount());
        assertEquals(45, roomHotIndexUnderTest.getShareCount());
        assertEquals(55, roomHotIndexUnderTest.getConsumptionCount());
        assertEquals(65, roomHotIndexUnderTest.getMessageCount());
        assertEquals(75, roomHotIndexUnderTest.getNewFollowerCount());
        assertEquals(85, roomHotIndexUnderTest.getSumViewTime());
    }

    @Test
    void testToString() {
        String expectedToString = "RoomHotIndex(roomId=1, duration=10, viewCount=20, likeCount=30, shareCount=40, consumptionCount=50, messageCount=60, newFollowerCount=70, sumViewTime=80)";
        assertEquals(expectedToString, roomHotIndexUnderTest.toString());
    }

    @Test
    void testHashCode() {
        RoomHotIndex sameIndex = new RoomHotIndex(1, 10, 20, 30, 40, 50, 60, 70, 80);
        assertEquals(sameIndex.hashCode(), roomHotIndexUnderTest.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        RoomHotIndex emptyIndex = new RoomHotIndex();
        assertEquals(null, emptyIndex.getRoomId());
        assertEquals(0, emptyIndex.getDuration());
        assertEquals(0, emptyIndex.getViewCount());
        assertEquals(0, emptyIndex.getLikeCount());
        assertEquals(0, emptyIndex.getShareCount());
        assertEquals(0, emptyIndex.getConsumptionCount());
        assertEquals(0, emptyIndex.getMessageCount());
        assertEquals(0, emptyIndex.getNewFollowerCount());
        assertEquals(0, emptyIndex.getSumViewTime());
    }
}
