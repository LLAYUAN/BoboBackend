package org.example.userservice.service;

import org.example.userservice.dao.CompleteUserDao;
import org.example.userservice.dao.FollowerDao;
import org.example.userservice.dto.BasicUserDTO;
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
    UserInfoRepo userInfoRepository;

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


}
