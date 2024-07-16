package org.example.recordvideoservice.daoimpl;

import org.example.recordvideoservice.dao.UserInfoDao;
import org.example.recordvideoservice.entity.UserInfo;
import org.example.recordvideoservice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findUserInfoByUserID(Integer userID) {
        return userInfoRepository.findUserInfoByUserID(userID);
    }
}
