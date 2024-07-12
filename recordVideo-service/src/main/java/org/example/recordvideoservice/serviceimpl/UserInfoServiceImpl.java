package org.example.recordvideoservice.serviceimpl;

import org.example.recordvideoservice.dao.UserInfoDao;
import org.example.recordvideoservice.entity.UserInfo;
import org.example.recordvideoservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findUserInfoByUserID(Integer userID) {
        return userInfoDao.findUserInfoByUserID(userID);
    }

}
