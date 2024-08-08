//package org.example.recommendservice.utils;
//
//import org.example.recommendservice.entity.BrowsingRecord;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class SimilarIndexCalculatorTest {
//
//    @Test
//    public void testCalculateSimilarIndex() {
//        BrowsingRecord browsingRecord = new BrowsingRecord();
//        browsingRecord.setWatchDuration(30); // 30 minutes
//        browsingRecord.setLikeCount(10);
//        browsingRecord.setShareCount(5);
//        browsingRecord.setConsumptionCount(2);
//        browsingRecord.setMessageCount(50);
//        browsingRecord.setFollowStatus(1); // Followed
//
//        int similarIndex = LikeIndexCalculator.calculateLikeIndex(browsingRecord);
//
//        double expectedSimilarIndex = 0.0;
//        expectedSimilarIndex += 1.5; // VIEW_COUNT_WEIGHT
//        expectedSimilarIndex += 30 * (1.0 / 30); // VIEW_MINUTE_COUNT_WEIGHT
//        expectedSimilarIndex += 10 * 1.0; // LIKE_COUNT_WEIGHT
//        expectedSimilarIndex += 5 * 2.0; // SHARE_COUNT_WEIGHT
//        expectedSimilarIndex += 2 * 10.0; // CONSUMPTION_COUNT_WEIGHT
//        expectedSimilarIndex += 50 * 0.1; // MESSAGE_COUNT_WEIGHT
//        expectedSimilarIndex += 1 * 5.0; // NEW_FOLLOWER_COUNT_WEIGHT
//
//        assertEquals((int) expectedSimilarIndex, similarIndex);
//    }
//
//    @Test
//    public void testCalculateSimilarIndexWithZeroValues() {
//        BrowsingRecord browsingRecord = new BrowsingRecord();
//        browsingRecord.setWatchDuration(0);
//        browsingRecord.setLikeCount(0);
//        browsingRecord.setShareCount(0);
//        browsingRecord.setConsumptionCount(0);
//        browsingRecord.setMessageCount(0);
//        browsingRecord.setFollowStatus(0); // Not followed
//
//        int similarIndex = LikeIndexCalculator.calculateLikeIndex(browsingRecord);
//
//        double expectedSimilarIndex = 0.0;
//        expectedSimilarIndex += 1.5; // VIEW_COUNT_WEIGHT
//        expectedSimilarIndex += 0 * (1.0 / 30); // VIEW_MINUTE_COUNT_WEIGHT
//        expectedSimilarIndex += 0 * 1.0; // LIKE_COUNT_WEIGHT
//        expectedSimilarIndex += 0 * 2.0; // SHARE_COUNT_WEIGHT
//        expectedSimilarIndex += 0 * 10.0; // CONSUMPTION_COUNT_WEIGHT
//        expectedSimilarIndex += 0 * 0.1; // MESSAGE_COUNT_WEIGHT
//        expectedSimilarIndex += 0 * 5.0; // NEW_FOLLOWER_COUNT_WEIGHT
//
//        assertEquals((int) expectedSimilarIndex, similarIndex);
//    }
//
//    @Test
//    public void testCalculateSimilarIndexWithNegativeValues() {
//        BrowsingRecord browsingRecord = new BrowsingRecord();
//        browsingRecord.setWatchDuration(-30);
//        browsingRecord.setLikeCount(-10);
//        browsingRecord.setShareCount(-5);
//        browsingRecord.setConsumptionCount(-2);
//        browsingRecord.setMessageCount(-50);
//        browsingRecord.setFollowStatus(-1); // Unfollowed
//
//        int similarIndex = LikeIndexCalculator.calculateLikeIndex(browsingRecord);
//
//        double expectedSimilarIndex = 0.0;
//        expectedSimilarIndex += 1.5; // VIEW_COUNT_WEIGHT
//        expectedSimilarIndex += -30 * (1.0 / 30); // VIEW_MINUTE_COUNT_WEIGHT
//        expectedSimilarIndex += -10 * 1.0; // LIKE_COUNT_WEIGHT
//        expectedSimilarIndex += -5 * 2.0; // SHARE_COUNT_WEIGHT
//        expectedSimilarIndex += -2 * 10.0; // CONSUMPTION_COUNT_WEIGHT
//        expectedSimilarIndex += -50 * 0.1; // MESSAGE_COUNT_WEIGHT
//        expectedSimilarIndex += -1 * 5.0; // NEW_FOLLOWER_COUNT_WEIGHT
//
//        assertEquals((int) expectedSimilarIndex, similarIndex);
//    }
//
//    @Test
//    public void testCalculateSimilarIndexWithLargeValues() {
//        BrowsingRecord browsingRecord = new BrowsingRecord();
//        browsingRecord.setWatchDuration(10000);
//        browsingRecord.setLikeCount(1000);
//        browsingRecord.setShareCount(500);
//        browsingRecord.setConsumptionCount(200);
//        browsingRecord.setMessageCount(5000);
//        browsingRecord.setFollowStatus(1); // Followed
//
//        int similarIndex = LikeIndexCalculator.calculateLikeIndex(browsingRecord);
//
//        double expectedSimilarIndex = 0.0;
//        expectedSimilarIndex += 1.5; // VIEW_COUNT_WEIGHT
//        expectedSimilarIndex += 10000 * (1.0 / 30); // VIEW_MINUTE_COUNT_WEIGHT
//        expectedSimilarIndex += 1000 * 1.0; // LIKE_COUNT_WEIGHT
//        expectedSimilarIndex += 500 * 2.0; // SHARE_COUNT_WEIGHT
//        expectedSimilarIndex += 200 * 10.0; // CONSUMPTION_COUNT_WEIGHT
//        expectedSimilarIndex += 5000 * 0.1; // MESSAGE_COUNT_WEIGHT
//        expectedSimilarIndex += 1 * 5.0; // NEW_FOLLOWER_COUNT_WEIGHT
//
//        assertEquals((int) expectedSimilarIndex, similarIndex);
//    }
//}
