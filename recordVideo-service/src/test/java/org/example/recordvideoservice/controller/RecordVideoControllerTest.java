package org.example.recordvideoservice.controller;

import org.example.recordvideoservice.entity.RecordVideo;
import org.example.recordvideoservice.entity.UserInfo;
import org.example.recordvideoservice.service.RecordVideoService;
import org.example.recordvideoservice.utils.RecordVideoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecordVideoController.class)
class RecordVideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecordVideoService mockRecordVideoService;
    @MockBean
    private RecordVideoUtils mockRecordVideoUtils;

    @Test
    void testGetUsersRecordVideos() throws Exception {
        // Setup
        // Configure RecordVideoService.findByUserid(...).
        final RecordVideo recordVideo = new RecordVideo();
        recordVideo.setRecordVideoID(0);
        recordVideo.setRecordVideoName("recordVideoName");
        recordVideo.setRecordVideoIntro("recordVideoIntro");
        recordVideo.setRecordVideoCoverUrl("imageUrl");
        recordVideo.setRecordVideoAddress("recordVideoAddress");
        recordVideo.setRecordVideoUploadTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final UserInfo userInfo = new UserInfo();
        userInfo.setUserID(0);
        userInfo.setAvatarUrl("avatarUrl");
        userInfo.setNickname("nickname");
        userInfo.setSelfIntro("ownerIntro");
        recordVideo.setUserInfo(userInfo);
        final List<RecordVideo> recordVideos = List.of(recordVideo);
        when(mockRecordVideoService.findByUserid(0)).thenReturn(recordVideos);

        // Run the test and verify the results
        mockMvc.perform(get("/getUsersRecordVideos")
                        .param("userID", "string_userID")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetUsersRecordVideos_RecordVideoServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRecordVideoService.findByUserid(0)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/getUsersRecordVideos")
                        .param("userID", "string_userID")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", true));
    }

    @Test
    void testGetPlayingRecordVideo() throws Exception {
        // Setup
        // Configure RecordVideoService.findRecordVideoByRecordVideoID(...).
        final RecordVideo recordVideo = new RecordVideo();
        recordVideo.setRecordVideoID(0);
        recordVideo.setRecordVideoName("recordVideoName");
        recordVideo.setRecordVideoIntro("recordVideoIntro");
        recordVideo.setRecordVideoCoverUrl("imageUrl");
        recordVideo.setRecordVideoAddress("recordVideoAddress");
        recordVideo.setRecordVideoUploadTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final UserInfo userInfo = new UserInfo();
        userInfo.setUserID(0);
        userInfo.setAvatarUrl("avatarUrl");
        userInfo.setNickname("nickname");
        userInfo.setSelfIntro("ownerIntro");
        recordVideo.setUserInfo(userInfo);
        when(mockRecordVideoService.findRecordVideoByRecordVideoID(0)).thenReturn(recordVideo);

        // Run the test and verify the results
        mockMvc.perform(get("/getPlayingRecordVideo")
                        .param("videoID", "string_videoID")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testUploadFile() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(multipart("/uploadFile")
                        .file(new MockMultipartFile("file", "originalFilename", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testDeleteFile() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/deleteFile")
                        .param("fileName", "fileName")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testSaveRecordVideo() throws Exception {
        // Setup
        // Configure RecordVideoUtils.transRecordVideoFromFrontend(...).
        final RecordVideo recordVideo = new RecordVideo();
        recordVideo.setRecordVideoID(0);
        recordVideo.setRecordVideoName("recordVideoName");
        recordVideo.setRecordVideoIntro("recordVideoIntro");
        recordVideo.setRecordVideoCoverUrl("imageUrl");
        recordVideo.setRecordVideoAddress("recordVideoAddress");
        recordVideo.setRecordVideoUploadTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final UserInfo userInfo = new UserInfo();
        userInfo.setUserID(0);
        userInfo.setAvatarUrl("avatarUrl");
        userInfo.setNickname("nickname");
        userInfo.setSelfIntro("ownerIntro");
        recordVideo.setUserInfo(userInfo);
        when(mockRecordVideoUtils.transRecordVideoFromFrontend(Map.ofEntries(Map.entry("value", "value")),
                0)).thenReturn(recordVideo);

        // Run the test and verify the results
        mockMvc.perform(post("/saveRecordVideo")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));

        // Confirm RecordVideoService.saveRecordVideo(...).
        final RecordVideo recordVideo1 = new RecordVideo();
        recordVideo1.setRecordVideoID(0);
        recordVideo1.setRecordVideoName("recordVideoName");
        recordVideo1.setRecordVideoIntro("recordVideoIntro");
        recordVideo1.setRecordVideoCoverUrl("imageUrl");
        recordVideo1.setRecordVideoAddress("recordVideoAddress");
        recordVideo1.setRecordVideoUploadTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final UserInfo userInfo1 = new UserInfo();
        userInfo1.setUserID(0);
        userInfo1.setAvatarUrl("avatarUrl");
        userInfo1.setNickname("nickname");
        userInfo1.setSelfIntro("ownerIntro");
        recordVideo1.setUserInfo(userInfo1);
        verify(mockRecordVideoService).saveRecordVideo(recordVideo1);
    }
}
