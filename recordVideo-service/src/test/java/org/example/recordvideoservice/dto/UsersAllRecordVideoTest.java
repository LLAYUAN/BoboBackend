package org.example.recordvideoservice.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UsersAllRecordVideoTest {

    private UsersAllRecordVideo usersAllRecordVideoUnderTest;

    @BeforeEach
    void setUp() {
        usersAllRecordVideoUnderTest = new UsersAllRecordVideo();
    }

    @Test
    void testGettersAndSetters() {
        // Setup
        Integer videoID = 1;
        String videoName = "videoName";
        String videoIntro = "videoIntro";
        String ownerName = "ownerName";
        String imageUrl = "http://example.com/image.jpg";
        LocalDateTime uploadTime = LocalDateTime.of(2023, 1, 1, 12, 0);

        // Set values
        usersAllRecordVideoUnderTest.setVideoID(videoID);
        usersAllRecordVideoUnderTest.setVideoName(videoName);
        usersAllRecordVideoUnderTest.setVideoIntro(videoIntro);
        usersAllRecordVideoUnderTest.setOwnerName(ownerName);
        usersAllRecordVideoUnderTest.setImageUrl(imageUrl);
        usersAllRecordVideoUnderTest.setUploadTime(uploadTime);

        // Verify the results
        assertThat(usersAllRecordVideoUnderTest.getVideoID()).isEqualTo(videoID);
        assertThat(usersAllRecordVideoUnderTest.getVideoName()).isEqualTo(videoName);
        assertThat(usersAllRecordVideoUnderTest.getVideoIntro()).isEqualTo(videoIntro);
        assertThat(usersAllRecordVideoUnderTest.getOwnerName()).isEqualTo(ownerName);
        assertThat(usersAllRecordVideoUnderTest.getImageUrl()).isEqualTo(imageUrl);
        assertThat(usersAllRecordVideoUnderTest.getUploadTime()).isEqualTo(uploadTime);
    }

    @Test
    void testNoArgsConstructor() {
        // Verify the results
        assertThat(usersAllRecordVideoUnderTest).isNotNull();
        assertThat(usersAllRecordVideoUnderTest.getVideoID()).isNull();
        assertThat(usersAllRecordVideoUnderTest.getVideoName()).isNull();
        assertThat(usersAllRecordVideoUnderTest.getVideoIntro()).isNull();
        assertThat(usersAllRecordVideoUnderTest.getOwnerName()).isNull();
        assertThat(usersAllRecordVideoUnderTest.getImageUrl()).isNull();
        assertThat(usersAllRecordVideoUnderTest.getUploadTime()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Setup
        Integer videoID = 1;
        String videoName = "videoName";
        String videoIntro = "videoIntro";
        String ownerName = "ownerName";
        String imageUrl = "http://example.com/image.jpg";
        LocalDateTime uploadTime = LocalDateTime.of(2023, 1, 1, 12, 0);

        // Run the test
        UsersAllRecordVideo usersAllRecordVideo = new UsersAllRecordVideo();
        usersAllRecordVideo.setVideoID(videoID);
        usersAllRecordVideo.setVideoName(videoName);
        usersAllRecordVideo.setVideoIntro(videoIntro);
        usersAllRecordVideo.setOwnerName(ownerName);
        usersAllRecordVideo.setImageUrl(imageUrl);
        usersAllRecordVideo.setUploadTime(uploadTime);

        // Verify the results
        assertThat(usersAllRecordVideo.getVideoID()).isEqualTo(videoID);
        assertThat(usersAllRecordVideo.getVideoName()).isEqualTo(videoName);
        assertThat(usersAllRecordVideo.getVideoIntro()).isEqualTo(videoIntro);
        assertThat(usersAllRecordVideo.getOwnerName()).isEqualTo(ownerName);
        assertThat(usersAllRecordVideo.getImageUrl()).isEqualTo(imageUrl);
        assertThat(usersAllRecordVideo.getUploadTime()).isEqualTo(uploadTime);
    }
}
