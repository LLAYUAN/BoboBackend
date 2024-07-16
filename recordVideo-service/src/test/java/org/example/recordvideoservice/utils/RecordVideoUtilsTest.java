package org.example.recordvideoservice.utils;

import org.example.recordvideoservice.entity.RecordVideo;
import org.example.recordvideoservice.entity.UserInfo;
import org.example.recordvideoservice.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordVideoUtilsTest {

    @Mock
    private UserInfoService mockUserInfoService;

    @InjectMocks
    private RecordVideoUtils recordVideoUtilsUnderTest;

    @Test
    void testTransRecordVideoFromFrontend() {
        // Setup

        Integer userID = 1;
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(userID);
        when(mockUserInfoService.findUserInfoByUserID(userID)).thenReturn(userInfo);

        Map<String, Object> recordVideoData = new HashMap<>();
        recordVideoData.put("videoName", "testVideo");
        recordVideoData.put("videoIntro", "testIntro");
        recordVideoData.put("imageUrl", "http://test.com/image.jpg");
        recordVideoData.put("videoUrl", "http://test.com/video.mp4");

        LocalDateTime now = LocalDateTime.now();

        // Run the test
        RecordVideo result = recordVideoUtilsUnderTest.transRecordVideoFromFrontend(recordVideoData, userID);

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result.getRecordVideoName()).isEqualTo("testVideo");
        assertThat(result.getRecordVideoIntro()).isEqualTo("testIntro");
        assertThat(result.getRecordVideoCoverUrl()).isEqualTo("http://test.com/image.jpg");
        assertThat(result.getRecordVideoAddress()).isEqualTo("http://test.com/video.mp4");
        assertThat(result.getUserInfo()).isEqualTo(userInfo);
        assertThat(result.getRecordVideoUploadTime()).isNotNull();
        assertThat(result.getRecordVideoUploadTime()).isAfterOrEqualTo(now.minusSeconds(1));
        assertThat(result.getRecordVideoUploadTime()).isBeforeOrEqualTo(now.plusSeconds(1));
    }
}
