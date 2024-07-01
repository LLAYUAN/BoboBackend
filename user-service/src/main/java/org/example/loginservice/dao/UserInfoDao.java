package org.example.loginservice.dao;

import org.example.loginservice.entity.UserInfo;
import org.example.loginservice.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDao {
    @Autowired
    private UserInfoRepo userInfoRepo;

    public UserInfo findByEmail(String email) {
        return userInfoRepo.findByEmail(email);
    }
}
