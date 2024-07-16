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

import java.io.IOException;
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
    private ObjectMapper objectMapper; // Jackson ObjectMapper
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
        if (roomHotIndex == null) {
            roomHotIndex = new RoomHotIndex();
            roomHotIndex.setRoomId(addHotIndex.getRoomId());
        }
        roomHotIndex.setViewCount(roomHotIndex.getViewCount() + addHotIndex.getViewCount());
        roomHotIndex.setLikeCount(roomHotIndex.getLikeCount() + addHotIndex.getLikeCount());
        roomHotIndex.setShareCount(roomHotIndex.getShareCount() + addHotIndex.getShareCount());
        roomHotIndex.setConsumptionCount(roomHotIndex.getConsumptionCount() + addHotIndex.getConsumptionCount());
        roomHotIndex.setMessageCount(roomHotIndex.getMessageCount() + addHotIndex.getMessageCount());
        roomHotIndex.setNewFollowerCount(roomHotIndex.getNewFollowerCount() + addHotIndex.getNewFollowerCount());
        roomHotIndex.setSumViewTime(roomHotIndex.getSumViewTime() + addHotIndex.getSumViewTime());

        roomDao.saveRoomHotIndex(roomHotIndex);
//        roomDao.addRoomHotIndex(addHotIndex);
    }

}
