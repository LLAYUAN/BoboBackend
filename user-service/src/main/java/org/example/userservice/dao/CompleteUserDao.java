package org.example.userservice.dao;

import org.example.userservice.entity.UserInfo;
import org.example.userservice.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompleteUserDao {
    @Autowired
    private UserInfoRepo userInfoRepo;

    public UserInfo findUserInfoByEmail(String email) {
        return userInfoRepo.findByEmail(email);
    }
}
