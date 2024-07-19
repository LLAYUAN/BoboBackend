package org.example.recommendservice.utils;

import org.example.recommendservice.entity.RoomHotIndex;
import org.example.recommendservice.entity.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class HotIndexCalculator {


    private static final double VIEW_COUNT_WEIGHT = 1.5;
    private static final double AVERAGE_VIEW_COUNT_WEIGHT = 1.0;
    private static final double LIKE_COUNT_WEIGHT = 1.0;
    private static final double SHARE_COUNT_WEIGHT = 2.0;
    private static final double NEW_FOLLOWER_COUNT_WEIGHT = 5.0;
    private static final double MESSAGE_COUNT_WEIGHT = 0.1;
    private static final double CONSUMPTION_COUNT_WEIGHT = 10.0;
    public static int calculateHotIndex(RoomHotIndex roomHotIndex) {
        double hotIndex = 0.0;
        double averageViewCount = 0.0;
        if(roomHotIndex.getDuration()!=0) {
            averageViewCount = (double) roomHotIndex.getSumViewTime() / roomHotIndex.getDuration();
        }
        else{
            averageViewCount = (double) roomHotIndex.getSumViewTime() / 2000;
        }
        System.out.println("roomHotIndex: "+ roomHotIndex);
        System.out.println("initial hotIndex: " + hotIndex);

        hotIndex += roomHotIndex.getViewCount() * VIEW_COUNT_WEIGHT;
        hotIndex += averageViewCount * AVERAGE_VIEW_COUNT_WEIGHT;
        hotIndex += roomHotIndex.getLikeCount() * LIKE_COUNT_WEIGHT;
        hotIndex += roomHotIndex.getShareCount() * SHARE_COUNT_WEIGHT;
        hotIndex += roomHotIndex.getNewFollowerCount() * NEW_FOLLOWER_COUNT_WEIGHT;
        hotIndex += roomHotIndex.getMessageCount() * MESSAGE_COUNT_WEIGHT;
        hotIndex += roomHotIndex.getConsumptionCount() * CONSUMPTION_COUNT_WEIGHT;
        System.out.println("hotIndex: " + hotIndex);
        return (int) hotIndex;
    }
}
