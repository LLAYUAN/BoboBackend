package org.example.recommendservice.MySQLRepository;


import org.example.recommendservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {
    public UserInfo findByEmail(String email);
    public UserInfo findByUserID(Integer userID);
    public UserInfo save(UserInfo userInfo);
}
