package org.example.recordvideoservice.entity;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecordVideoTest {

    private RecordVideo recordVideoUnderTest;

    @BeforeEach
    void setUp() {
        recordVideoUnderTest = new RecordVideo();
    }


    @Test
    void testGettersAndSetters() {
        // Setup
        Integer recordVideoID = 1;
        String recordVideoName = "Test Video";
        String recordVideoIntro = "Test Intro";
        String recordVideoCoverUrl = "http://test.com/image.jpg";
        String recordVideoAddress = "http://test.com/video.mp4";
        LocalDateTime recordVideoUploadTime = LocalDateTime.now();
        UserInfo userInfo = new UserInfo();

        // Set values
        recordVideoUnderTest.setRecordVideoID(recordVideoID);
        recordVideoUnderTest.setRecordVideoName(recordVideoName);
        recordVideoUnderTest.setRecordVideoIntro(recordVideoIntro);
        recordVideoUnderTest.setRecordVideoCoverUrl(recordVideoCoverUrl);
        recordVideoUnderTest.setRecordVideoAddress(recordVideoAddress);
        recordVideoUnderTest.setRecordVideoUploadTime(recordVideoUploadTime);
        recordVideoUnderTest.setUserInfo(userInfo);

        // Verify the results
        assertThat(recordVideoUnderTest.getRecordVideoID()).isEqualTo(recordVideoID);
        assertThat(recordVideoUnderTest.getRecordVideoName()).isEqualTo(recordVideoName);
        assertThat(recordVideoUnderTest.getRecordVideoIntro()).isEqualTo(recordVideoIntro);
        assertThat(recordVideoUnderTest.getRecordVideoCoverUrl()).isEqualTo(recordVideoCoverUrl);
        assertThat(recordVideoUnderTest.getRecordVideoAddress()).isEqualTo(recordVideoAddress);
        assertThat(recordVideoUnderTest.getRecordVideoUploadTime()).isEqualTo(recordVideoUploadTime);
        assertThat(recordVideoUnderTest.getUserInfo()).isEqualTo(userInfo);
    }

    @Test
    void testNoArgsConstructor() {
        // Verify the results
        assertThat(recordVideoUnderTest).isNotNull();
        assertThat(recordVideoUnderTest.getRecordVideoID()).isNull();
        assertThat(recordVideoUnderTest.getRecordVideoName()).isNull();
        assertThat(recordVideoUnderTest.getRecordVideoIntro()).isNull();
        assertThat(recordVideoUnderTest.getRecordVideoCoverUrl()).isNull();
        assertThat(recordVideoUnderTest.getRecordVideoAddress()).isNull();
        assertThat(recordVideoUnderTest.getRecordVideoUploadTime()).isNull();
        assertThat(recordVideoUnderTest.getUserInfo()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Setup
        Integer recordVideoID = 1;
        String recordVideoName = "Test Video";
        String recordVideoIntro = "Test Intro";
        String recordVideoCoverUrl = "http://test.com/image.jpg";
        String recordVideoAddress = "http://test.com/video.mp4";
        LocalDateTime recordVideoUploadTime = LocalDateTime.now();
        UserInfo userInfo = new UserInfo();

        // Run the test
        RecordVideo recordVideo = new RecordVideo();
        recordVideo.setRecordVideoID(recordVideoID);
        recordVideo.setRecordVideoName(recordVideoName);
        recordVideo.setRecordVideoIntro(recordVideoIntro);
        recordVideo.setRecordVideoCoverUrl(recordVideoCoverUrl);
        recordVideo.setRecordVideoAddress(recordVideoAddress);
        recordVideo.setRecordVideoUploadTime(recordVideoUploadTime);
        recordVideo.setUserInfo(userInfo);

        // Verify the results
        assertThat(recordVideo.getRecordVideoID()).isEqualTo(recordVideoID);
        assertThat(recordVideo.getRecordVideoName()).isEqualTo(recordVideoName);
        assertThat(recordVideo.getRecordVideoIntro()).isEqualTo(recordVideoIntro);
        assertThat(recordVideo.getRecordVideoCoverUrl()).isEqualTo(recordVideoCoverUrl);
        assertThat(recordVideo.getRecordVideoAddress()).isEqualTo(recordVideoAddress);
        assertThat(recordVideo.getRecordVideoUploadTime()).isEqualTo(recordVideoUploadTime);
        assertThat(recordVideo.getUserInfo()).isEqualTo(userInfo);
    }
}
