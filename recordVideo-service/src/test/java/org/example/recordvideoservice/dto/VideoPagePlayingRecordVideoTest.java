package org.example.recordvideoservice.dto;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VideoPagePlayingRecordVideoTest {

    private VideoPagePlayingRecordVideo videoPagePlayingRecordVideoUnderTest;

    @BeforeEach
    void setUp() {
        videoPagePlayingRecordVideoUnderTest = new VideoPagePlayingRecordVideo();
    }


    @Test
    void testGettersAndSetters() {
        // Setup
        String videoName = "videoName";
        String videoIntro = "videoIntro";
        String videoUrl = "http://example.com/video.mp4";
        Integer ownerUserID = 1;
        String ownerName = "ownerName";
        String ownerIntro = "ownerIntro";
        String ownerAvatarUrl = "http://example.com/avatar.jpg";
        LocalDateTime uploadTime = LocalDateTime.of(2023, 1, 1, 12, 0);

        // Set values
        videoPagePlayingRecordVideoUnderTest.setVideoName(videoName);
        videoPagePlayingRecordVideoUnderTest.setVideoIntro(videoIntro);
        videoPagePlayingRecordVideoUnderTest.setVideoUrl(videoUrl);
        videoPagePlayingRecordVideoUnderTest.setOwnerUserID(ownerUserID);
        videoPagePlayingRecordVideoUnderTest.setOwnerName(ownerName);
        videoPagePlayingRecordVideoUnderTest.setOwnerIntro(ownerIntro);
        videoPagePlayingRecordVideoUnderTest.setOwnerAvatarUrl(ownerAvatarUrl);
        videoPagePlayingRecordVideoUnderTest.setUploadTime(uploadTime);

        // Verify the results
        assertThat(videoPagePlayingRecordVideoUnderTest.getVideoName()).isEqualTo(videoName);
        assertThat(videoPagePlayingRecordVideoUnderTest.getVideoIntro()).isEqualTo(videoIntro);
        assertThat(videoPagePlayingRecordVideoUnderTest.getVideoUrl()).isEqualTo(videoUrl);
        assertThat(videoPagePlayingRecordVideoUnderTest.getOwnerUserID()).isEqualTo(ownerUserID);
        assertThat(videoPagePlayingRecordVideoUnderTest.getOwnerName()).isEqualTo(ownerName);
        assertThat(videoPagePlayingRecordVideoUnderTest.getOwnerIntro()).isEqualTo(ownerIntro);
        assertThat(videoPagePlayingRecordVideoUnderTest.getOwnerAvatarUrl()).isEqualTo(ownerAvatarUrl);
        assertThat(videoPagePlayingRecordVideoUnderTest.getUploadTime()).isEqualTo(uploadTime);
    }

    @Test
    void testNoArgsConstructor() {
        // Verify the results
        assertThat(videoPagePlayingRecordVideoUnderTest).isNotNull();
        assertThat(videoPagePlayingRecordVideoUnderTest.getVideoName()).isNull();
        assertThat(videoPagePlayingRecordVideoUnderTest.getVideoIntro()).isNull();
        assertThat(videoPagePlayingRecordVideoUnderTest.getVideoUrl()).isNull();
        assertThat(videoPagePlayingRecordVideoUnderTest.getOwnerUserID()).isNull();
        assertThat(videoPagePlayingRecordVideoUnderTest.getOwnerName()).isNull();
        assertThat(videoPagePlayingRecordVideoUnderTest.getOwnerIntro()).isNull();
        assertThat(videoPagePlayingRecordVideoUnderTest.getOwnerAvatarUrl()).isNull();
        assertThat(videoPagePlayingRecordVideoUnderTest.getUploadTime()).isNull();
    }
}
