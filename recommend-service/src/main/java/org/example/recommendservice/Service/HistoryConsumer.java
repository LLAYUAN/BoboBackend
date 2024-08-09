package org.example.recommendservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.DTO.UserBrowsingRecord;
import org.example.recommendservice.Dao.BrowsHistoryDao;
import org.example.recommendservice.entity.BrowsingRecord;
import org.example.recommendservice.entity.UserBrowsHistory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class HistoryConsumer {

    @Setter
    @Getter
    private ConcurrentLinkedQueue<UserBrowsingRecord> messageQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    BrowsHistoryDao browsHistoryDao;

    @RabbitListener(queues = "historyQueue")
    public void receiveMessage(String temp) throws JsonProcessingException {
        UserBrowsingRecord userBrowsingRecord = objectMapper.readValue(temp, UserBrowsingRecord.class);
        messageQueue.add(userBrowsingRecord);
    }

    public HistoryConsumer() {
        //定时执行任务
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::processMessages, 5, 5, TimeUnit.SECONDS);
    }

    public void processMessages() {
        Map<String, List<BrowsingRecord>> map = new HashMap<>();
        UserBrowsingRecord userBrowsingRecord;

        while ((userBrowsingRecord = messageQueue.poll()) != null) {
//            System.out.println("Received message: " + userBrowsingRecord);
            String userId = userBrowsingRecord.getUserId();
            List<BrowsingRecord> browsingRecordList = new ArrayList<>();
            browsingRecordList.add(userBrowsingRecord.getBrowsingRecord());
            map.merge(userId, browsingRecordList, (oldList, newList) -> {
                newList.addAll(oldList);
                return newList;
            });
        }
        map.forEach(this::addBrowsingRecord);
    }

    private static final int MAX_HISTORY_SIZE = 30; // 最多保存近30条
    public void addBrowsingRecord(String userId, List<BrowsingRecord> browsingRecordList) {
        System.out.println("userId: " + userId);
        System.out.println("new browsingRecord:");
        browsingRecordList.forEach(System.out::println);

        UserBrowsHistory userHistory = browsHistoryDao.getUserBrowsHistory(userId);
        if (userHistory != null) {
            System.out.println("find by userId");
            browsingRecordList.addAll(userHistory.getBrowsingHistory());
        }
        if (browsingRecordList.size() > MAX_HISTORY_SIZE) {
            browsingRecordList = browsingRecordList.subList(0, MAX_HISTORY_SIZE);
        }
        userHistory = new UserBrowsHistory(userId, browsingRecordList);
        browsHistoryDao.saveUserBrowsHistory(userHistory);

        System.out.println("Finished!  userId:  " + userId);
        System.out.println("new BrowsingHistory:");
        browsingRecordList.forEach(System.out::println);
    }
}
