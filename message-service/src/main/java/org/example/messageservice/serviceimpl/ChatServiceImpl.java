package org.example.messageservice.serviceimpl;

import org.example.messageservice.entity.ChatMessage;
import org.example.messageservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getHistoryMessages(Integer roomID, Instant timestamp){
        Pageable pageable = PageRequest.of(0, 20);  // 查询时指定一页的大小
        String collectionName = "messages_room_" + roomID;
        Query query = new Query(Criteria.where("timestamp").lt(timestamp));
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "timestamp"));
        List<ChatMessage> messages = mongoTemplate.find(query, ChatMessage.class, collectionName);
        return messages;
    }


    @Override
    public void deleteOldMessages(Instant cutoffTime) {
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        for (String collectionName : collectionNames) {
            if (collectionName.startsWith("messages_room_")) {
                Query query = new Query(Criteria.where("timestamp").lt(cutoffTime));
                mongoTemplate.remove(query, ChatMessage.class, collectionName);
            }
        }
        //判断集合是否为空，是的话就删除集合
        for (String collectionName : collectionNames) {
            if (collectionName.startsWith("messages_room_")) {
                Query query = new Query();
                if (mongoTemplate.find(query, ChatMessage.class, collectionName).isEmpty()) {
                    mongoTemplate.dropCollection(collectionName);
                }
            }
        }
    }

    @Override
    public void deleteRoomMessages(Integer roomID) {
        String collectionName = "messages_room_" + roomID;
        mongoTemplate.dropCollection(collectionName);
    }
}
