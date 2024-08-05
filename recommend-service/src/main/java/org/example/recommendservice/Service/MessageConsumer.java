package org.example.recommendservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.RoomHotIndex;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MessageConsumer {
    @Setter
    @Getter
    private ConcurrentLinkedQueue<AddHotIndex> messageQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RoomDao roomDao;

    @RabbitListener(queues = "addHotIndexQueue")
    public void receiveMessage(String temp) throws JsonProcessingException {
        AddHotIndex addHotIndex = objectMapper.readValue(temp, AddHotIndex.class);
        messageQueue.add(addHotIndex);
    }

    public MessageConsumer() {
        //定时执行任务
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::processMessages, 5, 5, TimeUnit.SECONDS);
    }

    public void processMessages() {
        Map<Integer, AddHotIndex> map = new HashMap<>();
        AddHotIndex addHotIndex;

        while ((addHotIndex = messageQueue.poll()) != null) {
            System.out.println("Received message: " + addHotIndex);
            int roomId = addHotIndex.getRoomId();
            map.merge(roomId, addHotIndex, (old, newIndex) -> {
                old.setConsumptionCount(old.getConsumptionCount() + newIndex.getConsumptionCount());
                old.setLikeCount(old.getLikeCount() + newIndex.getLikeCount());
                old.setMessageCount(old.getMessageCount() + newIndex.getMessageCount());
                old.setNewFollowerCount(old.getNewFollowerCount() + newIndex.getNewFollowerCount());
                old.setShareCount(old.getShareCount() + newIndex.getShareCount());
                old.setSumViewTime(old.getSumViewTime() + newIndex.getSumViewTime());
                old.setViewCount(old.getViewCount() + newIndex.getViewCount());
                return old;
            });
        }
        map.forEach((roomId, hotIndex) -> addRoomHotIndex(hotIndex));
    }

    public void addRoomHotIndex(AddHotIndex addHotIndex){
        RoomHotIndex roomHotIndex = roomDao.getRoomHotIndex(addHotIndex.getRoomId());
        // 没找到就是因为关闭了直播
        if (roomHotIndex == null) {
//            roomHotIndex = new RoomHotIndex();
//            roomHotIndex.setRoomId(addHotIndex.getRoomId());
            return;
        }
        int viewCount = roomHotIndex.getViewCount() + addHotIndex.getViewCount();
        int likeCount = roomHotIndex.getLikeCount() + addHotIndex.getLikeCount();
        int shareCount = roomHotIndex.getShareCount() + addHotIndex.getShareCount();
        int consumptionCount = roomHotIndex.getConsumptionCount() + addHotIndex.getConsumptionCount();
        int messageCount = roomHotIndex.getMessageCount() + addHotIndex.getMessageCount();
        int newFollowerCount = roomHotIndex.getNewFollowerCount() + addHotIndex.getNewFollowerCount();
        int sumViewTime = roomHotIndex.getSumViewTime() + addHotIndex.getSumViewTime();

        roomHotIndex.setViewCount(viewCount);
        roomHotIndex.setLikeCount(likeCount);
        roomHotIndex.setShareCount(shareCount);
        roomHotIndex.setConsumptionCount(consumptionCount);
        roomHotIndex.setMessageCount(messageCount);
        roomHotIndex.setNewFollowerCount(newFollowerCount);
        roomHotIndex.setSumViewTime(sumViewTime);
        roomHotIndex.setHotIndex(calculateHotIndex(roomHotIndex.getStartTime(), viewCount, likeCount, shareCount,
                consumptionCount, messageCount, newFollowerCount, sumViewTime));

        roomDao.saveRoomHotIndex(roomHotIndex);
    }
    private static final double VIEW_COUNT_WEIGHT = 1.5;
    private static final double AVERAGE_VIEW_COUNT_WEIGHT = 1.0;
    private static final double LIKE_COUNT_WEIGHT = 1.0;
    private static final double SHARE_COUNT_WEIGHT = 2.0;
    private static final double NEW_FOLLOWER_COUNT_WEIGHT = 5.0;
    private static final double MESSAGE_COUNT_WEIGHT = 0.1;
    private static final double CONSUMPTION_COUNT_WEIGHT = 10.0;

    public static int calculateHotIndex(LocalDateTime startTime, int viewCount, int likeCount, int shareCount,
                                        int consumptionCount, int messageCount, int newFollowerCount, int sumViewTime) {
        double hotIndex = 0.0;
        long seconds = Duration.between(startTime, LocalDateTime.now()).getSeconds();
        double averageViewCount = (double) sumViewTime / seconds;

        System.out.println("initial hotIndex: " + hotIndex);

        hotIndex += viewCount * VIEW_COUNT_WEIGHT;
        hotIndex += averageViewCount * AVERAGE_VIEW_COUNT_WEIGHT;
        hotIndex += likeCount * LIKE_COUNT_WEIGHT;
        hotIndex += shareCount * SHARE_COUNT_WEIGHT;
        hotIndex += consumptionCount * CONSUMPTION_COUNT_WEIGHT;
        hotIndex += messageCount * MESSAGE_COUNT_WEIGHT;
        hotIndex += newFollowerCount * NEW_FOLLOWER_COUNT_WEIGHT;

        System.out.println("hotIndex: " + hotIndex);
        return (int) hotIndex;
    }
}
