package org.example.userservice.service;

import cn.hutool.json.JSONObject;
import org.example.userservice.Feign.Feign;
import org.example.userservice.dao.CompleteUserDao;
import org.example.userservice.dao.FollowerDao;
import org.example.userservice.dao.RoomDao;
import org.example.userservice.dto.BasicUserDTO;
import org.example.userservice.entity.RoomHotIndex;
import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.repository.RoomInfoRepo;
import org.example.userservice.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    FollowerDao followerDao;

    @Autowired
    RoomDao roomDao;

    @Autowired
    UserInfoRepo userInfoRepository;

    @Autowired
    CompleteUserDao completeUserDao;

    @Autowired
    Feign feign;

    @Autowired
    RoomInfoRepo roomInfoRepo;

    public void follow(Integer followeeID, Integer followerID) {
        followerDao.saveFollowerInfo(followeeID, followerID);
    }

    public void unfollow(Integer followeeID, Integer followerID) {
        followerDao.deleteFollowerInfo(followeeID, followerID);
    }

    // 获取某人关注的所有人
    public List<BasicUserDTO> getFollowees(Integer userID) {
        List<Integer> followeeIDs = followerDao.findFolloweesByFollowerID(userID);
        // 根据 followees 的 userID 获取用户信息
        return userInfoRepository.findAllById(followeeIDs).stream()
                .map(userInfo -> new BasicUserDTO(userInfo.getUserID(), userInfo.getNickname()))
                .collect(Collectors.toList());
    }

    // 获取某人的所有粉丝
    public List<BasicUserDTO> getFollowers(Integer userID) {
        List<Integer> followerIDs = followerDao.findFollowersByFolloweeID(userID);
        // 根据 followers 的 userID 获取用户信息
        return userInfoRepository.findAllById(followerIDs).stream()
                .map(userInfo -> new BasicUserDTO(userInfo.getUserID(), userInfo.getNickname()))
                .collect(Collectors.toList());
    }

    public boolean checkIsFan(Integer followeeID, Integer followerID) {
        return followerDao.checkIsFan(followeeID, followerID);
    }

//    public void startLive(Integer userID,String name, Integer[] tags,String coverUrl) {
//        roomDao.startLive(userID, name, tags, coverUrl);
//        // 将直播间设为开播状态，并开辟对应的hotindex
//    }

    public Integer getFollowerCount(Integer userID) {
        return followerDao.getFollowerCount(userID);
    }

    public Integer getFolloweeCount(Integer userID) {
        return followerDao.getFolloweeCount(userID);
    }

    public Integer createRoom(Integer userID, String roomName, String coverUrl, List<Integer> tags) {
        UserInfo userInfo = completeUserDao.findUserInfoByUserID(userID);
        RoomInfo roomInfo = userInfo.getRoomInfo();
//        Boolean status = roomInfo.getStatus();
        roomInfo.setRoomName(roomName);
        roomInfo.setCoverUrl(coverUrl);
        // 如果tags中有0，study为true，如果有1，entertain为true，如果有2，other为true
        roomInfo.setStudy(tags.contains(0));
        roomInfo.setEntertain(tags.contains(1));
        roomInfo.setOther(tags.contains(2));
//        if(!status){
//            roomInfo.setStartTime(java.time.LocalDateTime.now());
//        }
        //调整为开播
//        roomInfo.setStatus(true);
        roomDao.saveRoomInfo(roomInfo);
        // 为开播直播间创建一个roomHotIndex
        JSONObject request = new JSONObject();
        request.put("roomId", roomInfo.getRoomID());
        Boolean[] tagsArray = new Boolean[3];
        tagsArray[0] = tags.contains(0);
        tagsArray[1] = tags.contains(1);
        tagsArray[2] = tags.contains(2);
        request.put("tags", tagsArray);

        // 为开播直播间创建一个 roomHotIndex
        feign.createRoomHotIndex(request);
        // 为开播直播间创建一个roomHotIndexList
        System.out.println("roomID = " + roomInfo.getRoomID());
        // 如果当前已经开播，则不会创建hotindex，也不会将所有值设为0
//        if(status){
//            return roomInfo.getRoomID();
//        }
//        List<RoomHotIndex> roomHotIndexList = new ArrayList<>();
//        RoomHotIndex roomHotIndex = new RoomHotIndex();
//        roomHotIndex.setRoomId(roomInfo.getRoomID());
//        roomHotIndex.setDuration(0);
//        roomHotIndex.setViewCount(0);
//        roomHotIndex.setLikeCount(0);
//        roomHotIndex.setShareCount(0);
//        roomHotIndex.setConsumptionCount(0);
//        roomHotIndex.setMessageCount(0);
//        roomHotIndex.setNewFollowerCount(0);
//        roomHotIndex.setSumViewTime(0);
//        roomHotIndexList.add(roomHotIndex);
//        //保存直播间信息
//        feign.saveRoomHotIndex(roomHotIndexList);
        return roomInfo.getRoomID();
    }

//    public void setStatus(Integer roomID, Boolean status){
//        RoomInfo roomInfo = roomDao.findRoomInfoByRoomID(roomID);
//        if(roomInfo.getStatus() == status){
//            return;
//        }
//        roomInfo.setStatus(status);
//        roomDao.saveRoomInfo(roomInfo);
//    }
}
