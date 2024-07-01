package org.example.loginservice.repository;

import org.example.loginservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserInfoRepo extends JpaRepository<UserInfo, String> {
    public UserInfo findByEmail(String email);
}
