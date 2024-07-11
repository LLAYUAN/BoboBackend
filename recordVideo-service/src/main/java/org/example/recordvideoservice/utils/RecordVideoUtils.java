package org.example.recordvideoservice.utils;

import org.example.recordvideoservice.entity.RecordVideo;
import org.example.recordvideoservice.entity.UserInfo;
import org.example.recordvideoservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class RecordVideoUtils {
    @Autowired
    private UserInfoService userInfoService;
    public RecordVideo transRecordVideoFromFrontend(Map<String, Object> recordVideoData, Integer userID) {
        RecordVideo recordVideo = new RecordVideo();
        UserInfo userInfo = userInfoService.findUserInfoByUserID(userID);
        LocalDateTime now = LocalDateTime.now();
        recordVideo.setRecordVideoName((String) recordVideoData.get("videoName"));
        recordVideo.setRecordVideoIntro((String) recordVideoData.get("videoIntro"));
        recordVideo.setRecordVideoCoverUrl((String) recordVideoData.get("imageUrl"));
        recordVideo.setRecordVideoAddress((String) recordVideoData.get("videoUrl"));
        System.out.println("timenow:");
        System.out.println(now);
        recordVideo.setRecordVideoUploadTime(now);
        recordVideo.setUserInfo(userInfo);
        return recordVideo;
    }
}
