package org.example.userservice.service;

import org.example.userservice.dao.CompleteUserDao;
import org.example.userservice.dao.FollowerDao;
import org.example.userservice.dao.RoomDao;
import org.example.userservice.dto.BasicUserDTO;
import org.example.userservice.entity.RoomInfo;
import org.example.userservice.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void startLive(Integer userID,String name, Integer[] tags,String coverUrl) {
        roomDao.startLive(userID, name, tags, coverUrl);
    }

    public Integer getFollowerCount(Integer userID) {
        return followerDao.getFollowerCount(userID);
    }

    public Integer getFolloweeCount(Integer userID) {
        return followerDao.getFolloweeCount(userID);
    }

    public Integer createRoom(Integer userID, String roomName, String coverUrl, List<Integer> tags) {
        RoomInfo roomInfo = completeUserDao.findUserInfoByUserID(userID).getRoomInfo();
        roomInfo.setRoomName(roomName);
        roomInfo.setCoverUrl(coverUrl);
        // 如果tags中有0，study为true，如果有1，entertain为true，如果有2，other为true
        roomInfo.setStudy(tags.contains(0));
        roomInfo.setEntertain(tags.contains(1));
        roomInfo.setOther(tags.contains(2));
        roomDao.saveRoomInfo(roomInfo);
        return roomInfo.getRoomID();
    }
}
