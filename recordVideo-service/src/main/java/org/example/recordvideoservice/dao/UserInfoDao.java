package org.example.recordvideoservice.dao;

import org.example.recordvideoservice.entity.UserInfo;

public interface UserInfoDao {
    UserInfo findUserInfoByUserID(Integer userID);
}
