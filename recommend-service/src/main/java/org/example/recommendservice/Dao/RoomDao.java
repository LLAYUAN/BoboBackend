package org.example.recommendservice.Dao;

import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.MySQLRepository.RoomInfoRepo;
import org.example.recommendservice.MySQLRepository.UserInfoRepo;
import org.example.recommendservice.entity.HotIndexCalculator;
import org.example.recommendservice.entity.RoomHotIndex;
import org.example.recommendservice.entity.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoomDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RoomInfoRepo roomInfoRepo;

//    public RoomHotIndex saveRoomHotIndex(RoomHotIndex roomHotIndex) {
//        return mongoTemplate.save(roomHotIndex);
//    }

    public void saveRoomHotIndexList(List<RoomHotIndex> roomHotIndexList) {
//        mongoTemplate.insert(roomHotIndexList, RoomHotIndex.class);
        for (RoomHotIndex roomHotIndex : roomHotIndexList) {
            mongoTemplate.save(roomHotIndex);
//            double hotIndex = HotIndexCalculator.calculateHotIndex(roomHotIndex);
//            System.out.println("Room ID: " + roomHotIndex.getRoomId() + " - Hot Index: " + hotIndex);
        }
    }

    public void addRoomHotIndex(AddHotIndex addHotIndex) {
        RoomHotIndex roomHotIndex = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(addHotIndex.getRoomId())), RoomHotIndex.class);
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
        mongoTemplate.save(roomHotIndex);
    }

    public List<RoomCardInfo> getRank(Integer tag) {
        List<RoomInfo> roomInfos = roomInfoRepo.findAll();
        List<RoomCardInfo> roomCardInfos = new java.util.ArrayList<>(roomInfos.stream()
                .filter(RoomInfo::getStatus)
                .map(RoomCardInfo::new)
                .filter(roomCardInfo -> tag < 0 || roomCardInfo.getTags().get(tag))
                .toList());

        roomCardInfos.forEach(roomCardInfo -> {
            RoomHotIndex roomHotIndex = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(roomCardInfo.getId())), RoomHotIndex.class);
            if (roomHotIndex != null) {
                roomCardInfo.setHotIndex(HotIndexCalculator.calculateHotIndex(roomHotIndex));
            }
        });

        roomCardInfos.sort(Comparator.comparing(RoomCardInfo::getHotIndex).reversed());

        return roomCardInfos;
    }

    public RoomCardInfo getRoomInfo(Integer id) {
        RoomInfo roomInfo = roomInfoRepo.findById(id).orElse(null);
        if (roomInfo == null) {
            return null;
        }
        RoomCardInfo roomCardInfo = new RoomCardInfo(roomInfo);
        RoomHotIndex roomHotIndex = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), RoomHotIndex.class);
        if (roomHotIndex != null) {
            roomCardInfo.setHotIndex(HotIndexCalculator.calculateHotIndex(roomHotIndex));
        }
        return roomCardInfo;
    }
    public List<RoomInfo> findAll() {
        return roomInfoRepo.findAll();
    }

    public RoomInfo findById(Integer id) {
        return roomInfoRepo.findById(id).orElse(null);
    }
}
