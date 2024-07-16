package org.example.userservice.dao;

import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.repository.RoomInfoRepo;
import org.example.userservice.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoomDao {
    @Autowired
    private RoomInfoRepo roomInfoRepo;

    @Autowired
    private UserInfoRepo userInfoRepo;

    public void startLive(Integer roomID) {
        // 创建roomID的roomHotIndex

    }

    public void saveRoomInfo(RoomInfo roomInfo) {
        roomInfoRepo.save(roomInfo);
    }

    public RoomInfo findRoomInfoByRoomID(Integer roomID) {
        return roomInfoRepo.findRoomInfoByRoomID(roomID);
    }
}
