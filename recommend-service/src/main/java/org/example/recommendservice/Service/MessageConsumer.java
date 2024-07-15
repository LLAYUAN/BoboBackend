package org.example.recommendservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.Dao.RoomDao;
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

    private void processMessages() {
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
    public void  addRoomHotIndex(AddHotIndex addHotIndex){
        roomDao.addRoomHotIndex(addHotIndex);
    }

}
