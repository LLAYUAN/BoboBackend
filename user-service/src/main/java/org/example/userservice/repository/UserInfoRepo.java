package org.example.userservice.repository;

import org.example.userservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInfo, String> {
    public UserInfo findByEmail(String email);
    public UserInfo save(UserInfo userInfo);
}
