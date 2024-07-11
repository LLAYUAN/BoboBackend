package org.example.recommendservice.entity;

public class SimilarIndexCalculator {
    private static final double VIEW_COUNT_WEIGHT = 1.5;
    private static final double VIEW_MINUTE_COUNT_WEIGHT  = 1.0 / 30;
    private static final double LIKE_COUNT_WEIGHT = 1.0;
    private static final double SHARE_COUNT_WEIGHT = 2.0;
    private static final double NEW_FOLLOWER_COUNT_WEIGHT = 5.0;
    private static final double MESSAGE_COUNT_WEIGHT = 0.1;
    private static final double CONSUMPTION_COUNT_WEIGHT = 10.0;

    public static int calculateSimilarIndex(BrowsingRecord browsingRecord) {
        double hotIndex = 0.0;

        hotIndex += VIEW_COUNT_WEIGHT;
        hotIndex += browsingRecord.getWatchDuration() * VIEW_MINUTE_COUNT_WEIGHT;
        hotIndex += browsingRecord.getLikeCount() * LIKE_COUNT_WEIGHT;
        hotIndex += browsingRecord.getShareCount() * SHARE_COUNT_WEIGHT;
        hotIndex += browsingRecord.getConsumptionCount() * CONSUMPTION_COUNT_WEIGHT;
        hotIndex += browsingRecord.getMessageCount() * MESSAGE_COUNT_WEIGHT;
        hotIndex += browsingRecord.getFollowStatus() * NEW_FOLLOWER_COUNT_WEIGHT;
        return (int) hotIndex;
    }
}
