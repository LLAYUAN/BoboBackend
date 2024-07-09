package org.example.userservice.repository;

import org.apache.catalina.User;
import org.example.userservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {
    public UserInfo findByEmail(String email);
    public UserInfo findByUserID(Integer userID);
    public UserInfo save(UserInfo userInfo);
}
