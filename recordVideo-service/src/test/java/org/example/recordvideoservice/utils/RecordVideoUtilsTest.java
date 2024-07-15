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
        final Map<String, Object> recordVideoData = Map.ofEntries(Map.entry("value", "value"));
        final RecordVideo expectedResult = new RecordVideo();
        expectedResult.setRecordVideoName("recordVideoName");
        expectedResult.setRecordVideoIntro("recordVideoIntro");
        expectedResult.setRecordVideoCoverUrl("recordVideoCoverUrl");
        expectedResult.setRecordVideoAddress("recordVideoAddress");
        expectedResult.setRecordVideoUploadTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final UserInfo userInfo = new UserInfo();
        expectedResult.setUserInfo(userInfo);

        // Configure UserInfoService.findUserInfoByUserID(...).
        final UserInfo userInfo1 = new UserInfo();
        userInfo1.setUserID(0);
        userInfo1.setPassword("password");
        userInfo1.setEmail("email");
        userInfo1.setAvatarUrl("avatarUrl");
        userInfo1.setNickname("nickname");
        when(mockUserInfoService.findUserInfoByUserID(0)).thenReturn(userInfo1);

        // Run the test
        final RecordVideo result = recordVideoUtilsUnderTest.transRecordVideoFromFrontend(recordVideoData, 0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
