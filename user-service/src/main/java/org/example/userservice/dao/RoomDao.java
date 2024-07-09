package org.example.userservice.dao;

import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.repository.RoomInfoRepo;
import org.example.userservice.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomDao {
    @Autowired
    private RoomInfoRepo roomInfoRepo;

    @Autowired
    private UserInfoRepo userInfoRepo;

    public void startLive(Integer userID, String name, Integer[] tags, String coverUrl) {
        UserInfo userInfo = userInfoRepo.findByUserID(userID);
        RoomInfo roomInfo = userInfo.getRoomInfo();
        if(roomInfo == null){
            roomInfo = new RoomInfo(name, "直播间", coverUrl);
            roomInfo.setUserInfo(userInfo);
            // TODO：设置tags
            roomInfoRepo.save(roomInfo);
        }
        else {
            roomInfo.setRoomName(name);
            roomInfo.setCoverUrl(coverUrl);
            // TODO：设置tags
            roomInfoRepo.save(roomInfo);
        }
    }
}
