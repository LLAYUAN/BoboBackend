package org.example.recordvideoservice.service;

import org.example.recordvideoservice.entity.UserInfo;

public interface UserInfoService {
    UserInfo findUserInfoByUserID(Integer userID);
}
