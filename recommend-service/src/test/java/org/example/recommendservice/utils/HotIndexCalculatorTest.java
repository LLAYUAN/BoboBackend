package org.example.recommendservice.utils;

import org.example.recommendservice.entity.RoomHotIndex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotIndexCalculatorTest {
//
//    @Test
//    public void testCalculateHotIndex() {
//        RoomHotIndex roomHotIndex = new RoomHotIndex();
//        roomHotIndex.setViewCount(100);
//        roomHotIndex.setSumViewTime(5000);
//        roomHotIndex.setDuration(100);
//        roomHotIndex.setLikeCount(50);
//        roomHotIndex.setShareCount(20);
//        roomHotIndex.setNewFollowerCount(10);
//        roomHotIndex.setMessageCount(200);
//        roomHotIndex.setConsumptionCount(5);
//
//        int hotIndex = HotIndexCalculator.calculateHotIndex(roomHotIndex);
//
//        double expectedHotIndex = 0.0;
//        expectedHotIndex += 100 * 1.5; // VIEW_COUNT_WEIGHT
//        expectedHotIndex += (5000.0 / 100) * 1.0; // AVERAGE_VIEW_COUNT_WEIGHT
//        expectedHotIndex += 50 * 1.0; // LIKE_COUNT_WEIGHT
//        expectedHotIndex += 20 * 2.0; // SHARE_COUNT_WEIGHT
//        expectedHotIndex += 10 * 5.0; // NEW_FOLLOWER_COUNT_WEIGHT
//        expectedHotIndex += 200 * 0.1; // MESSAGE_COUNT_WEIGHT
//        expectedHotIndex += 5 * 10.0; // CONSUMPTION_COUNT_WEIGHT
//
//        assertEquals((int) expectedHotIndex, hotIndex);
//    }
//
//    @Test
//    public void testCalculateHotIndexWithZeroValues() {
//        RoomHotIndex roomHotIndex = new RoomHotIndex();
//        roomHotIndex.setViewCount(0);
//        roomHotIndex.setSumViewTime(0);
//        roomHotIndex.setDuration(1); // to avoid division by zero
//        roomHotIndex.setLikeCount(0);
//        roomHotIndex.setShareCount(0);
//        roomHotIndex.setNewFollowerCount(0);
//        roomHotIndex.setMessageCount(0);
//        roomHotIndex.setConsumptionCount(0);
//
//        int hotIndex = HotIndexCalculator.calculateHotIndex(roomHotIndex);
//
//        assertEquals(0, hotIndex);
//    }
//
//    @Test
//    public void testCalculateHotIndexWithNegativeValues() {
//        RoomHotIndex roomHotIndex = new RoomHotIndex();
//        roomHotIndex.setViewCount(-100);
//        roomHotIndex.setSumViewTime(-5000);
//        roomHotIndex.setDuration(100);
//        roomHotIndex.setLikeCount(-50);
//        roomHotIndex.setShareCount(-20);
//        roomHotIndex.setNewFollowerCount(-10);
//        roomHotIndex.setMessageCount(-200);
//        roomHotIndex.setConsumptionCount(-5);
//
//        int hotIndex = HotIndexCalculator.calculateHotIndex(roomHotIndex);
//
//        double expectedHotIndex = 0.0;
//        expectedHotIndex += -100 * 1.5; // VIEW_COUNT_WEIGHT
//        expectedHotIndex += (-5000.0 / 100) * 1.0; // AVERAGE_VIEW_COUNT_WEIGHT
//        expectedHotIndex += -50 * 1.0; // LIKE_COUNT_WEIGHT
//        expectedHotIndex += -20 * 2.0; // SHARE_COUNT_WEIGHT
//        expectedHotIndex += -10 * 5.0; // NEW_FOLLOWER_COUNT_WEIGHT
//        expectedHotIndex += -200 * 0.1; // MESSAGE_COUNT_WEIGHT
//        expectedHotIndex += -5 * 10.0; // CONSUMPTION_COUNT_WEIGHT
//
//        assertEquals((int) expectedHotIndex, hotIndex);
//    }
//
//    @Test
//    public void testCalculateHotIndexWithDurationZero() {
//        RoomHotIndex roomHotIndex = new RoomHotIndex();
//        roomHotIndex.setViewCount(100);
//        roomHotIndex.setSumViewTime(5000);
//        roomHotIndex.setDuration(100);
//        roomHotIndex.setLikeCount(50);
//        roomHotIndex.setShareCount(20);
//        roomHotIndex.setNewFollowerCount(10);
//        roomHotIndex.setMessageCount(200);
//        roomHotIndex.setConsumptionCount(5);
//
//        int hotIndex = HotIndexCalculator.calculateHotIndex(roomHotIndex);
//
//        double expectedHotIndex = 0.0;
//        expectedHotIndex += 100 * 1.5; // VIEW_COUNT_WEIGHT
//        expectedHotIndex += (double) 5000 / 100 * 1.0; // AVERAGE_VIEW_COUNT_WEIGHT handled as zero
//        expectedHotIndex += 50 * 1.0; // LIKE_COUNT_WEIGHT
//        expectedHotIndex += 20 * 2.0; // SHARE_COUNT_WEIGHT
//        expectedHotIndex += 10 * 5.0; // NEW_FOLLOWER_COUNT_WEIGHT
//        expectedHotIndex += 200 * 0.1; // MESSAGE_COUNT_WEIGHT
//        expectedHotIndex += 5 * 10.0; // CONSUMPTION_COUNT_WEIGHT
//
//        assertEquals((int) expectedHotIndex, hotIndex);
//    }
}
