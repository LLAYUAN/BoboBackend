package org.example.recommendservice.Dao;

import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.MySQLRepository.RoomInfoRepo;
import org.example.recommendservice.entity.RoomHotIndex;
import org.example.recommendservice.entity.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class RoomDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RoomInfoRepo roomInfoRepo;

    public void saveRoomHotIndex(RoomHotIndex roomHotIndex) {
        mongoTemplate.save(roomHotIndex);
    }
    public void saveRoomHotIndexList(List<RoomHotIndex> roomHotIndexList) {
        for (RoomHotIndex roomHotIndex : roomHotIndexList) {
            mongoTemplate.save(roomHotIndex);
        }
    }

    public RoomInfo getRoomInfo(Integer id) {
        return roomInfoRepo.findById(id).orElse(null);
    }
    public List<RoomInfo> getAllRoomInfo() {
        return roomInfoRepo.findAll();
    }

    public RoomHotIndex getRoomHotIndex(Integer id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), RoomHotIndex.class);
    }
    public List<RoomHotIndex> getAllRoomHotIndex(){
        return mongoTemplate.findAll(RoomHotIndex.class);
    }

    public RoomInfo findById(Integer id) {
        return roomInfoRepo.findById(id).orElse(null);
    }
    public List<RoomInfo> findAll() {
        return roomInfoRepo.findAll();
    }

}
