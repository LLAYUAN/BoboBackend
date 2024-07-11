package org.example.userservice.dao;

import org.example.userservice.entity.UserInfo;
import org.example.userservice.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompleteUserDao {
    @Autowired
    private UserInfoRepo userInfoRepo;

    public UserInfo findUserInfoByEmail(String email) {
        return userInfoRepo.findByEmail(email);
    }

    public UserInfo findUserInfoByUserID(Integer userID) {
        return userInfoRepo.findByUserID(userID);
    }

    public UserInfo save(UserInfo userInfo) {
        return userInfoRepo.save(userInfo);
    }

//    public List<UserInfo> findAllByUserIDs(List<Integer> userIDs) {
//        return userInfoRepo.findAllByUserID(userIDs);
//    }
}
