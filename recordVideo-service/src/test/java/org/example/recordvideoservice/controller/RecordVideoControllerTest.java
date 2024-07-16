package org.example.recordvideoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.matches;
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
    void testGetUsersRecordVideosParamsIs0() throws Exception {
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
        when(mockRecordVideoService.findByUserid(1)).thenReturn(recordVideos);
        // 构造预期的JSON数组字符串
        String expectedJsonArray = "[{\"videoID\":0,\"videoName\":\"recordVideoName\",\"videoIntro\":\"recordVideoIntro\",\"ownerName\":\"nickname\",\"imageUrl\":\"imageUrl\",\"uploadTime\":\"2020-01-01T00:00:00\"}]";

        // Run the test and verify the results
        mockMvc.perform(get("/getUsersRecordVideos")
                        .param("userID", "0")
                        .header("Authorization", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonArray));
    }

    @Test
    void testGetUsersRecordVideosParamsNot0() throws Exception {
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
        when(mockRecordVideoService.findByUserid(1)).thenReturn(recordVideos);
        // 构造预期的JSON数组字符串
        String expectedJsonArray = "[{\"videoID\":0,\"videoName\":\"recordVideoName\",\"videoIntro\":\"recordVideoIntro\",\"ownerName\":\"nickname\",\"imageUrl\":\"imageUrl\",\"uploadTime\":\"2020-01-01T00:00:00\"}]";

        // Run the test and verify the results
        mockMvc.perform(get("/getUsersRecordVideos")
                        .param("userID", "1")
                        .header("Authorization", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonArray));
    }

    @Test
    void testGetUsersRecordVideos_RecordVideoServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRecordVideoService.findByUserid(0)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/getUsersRecordVideos")
                        .param("userID", "1")
                        .header("Authorization", "1")
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
        recordVideo.setRecordVideoUploadTime(LocalDateTime.of(2020, 1, 1, 0, 0,0));
        final UserInfo userInfo = new UserInfo();
        userInfo.setUserID(0);
        userInfo.setAvatarUrl("avatarUrl");
        userInfo.setNickname("nickname");
        userInfo.setSelfIntro("ownerIntro");
        recordVideo.setUserInfo(userInfo);
        when(mockRecordVideoService.findRecordVideoByRecordVideoID(0)).thenReturn(recordVideo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedUploadTime = recordVideo.getRecordVideoUploadTime().format(formatter);


        String expectedJson = "{"
                + "\"videoName\":\"" + recordVideo.getRecordVideoName() + "\","
                + "\"videoIntro\":\"" + recordVideo.getRecordVideoIntro() + "\","
                + "\"videoUrl\":\"" + recordVideo.getRecordVideoAddress() + "\","
                + "\"ownerUserID\":" + userInfo.getUserID() + ","
                + "\"ownerName\":\"" + userInfo.getNickname() + "\","
                + "\"ownerIntro\":\"" + userInfo.getSelfIntro() + "\","
                + "\"uploadTime\":\"" + formattedUploadTime + "\","
                + "\"ownerAvatarUrl\":\"" + userInfo.getAvatarUrl() + "\""
                + "}";

        // Run the test and verify the results
        mockMvc.perform(get("/getPlayingRecordVideo")
                        .param("videoID", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testUploadFile() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(multipart("/uploadFile")
                        .file(new MockMultipartFile("file", "video.mp4", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        // 验证响应体是否是一个有效的URL
                .andExpect(content().string(matchesPattern("http://localhost:9999/recordvideo/resources/[0-9a-fA-F-]+\\.mp4")));
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
        // 构造JSON请求体
        Map<String, Object> recordVideoData = new HashMap<>();
        recordVideoData.put("videoName", "recordVideoName");
        recordVideoData.put("videoIntro", "recordVideoIntro");
        recordVideoData.put("imageUrl", "imageUrl");
        recordVideoData.put("videoUrl", "recordVideoAddress");

        // 将Map转换为JSON字符串
        String jsonContent = new ObjectMapper().writeValueAsString(recordVideoData);

        // 执行测试
        mockMvc.perform(post("/saveRecordVideo")
                        .content(jsonContent) // 使用构造的JSON字符串作为请求体
                        .contentType(MediaType.APPLICATION_JSON) // 指定内容类型为application/json
                        .header("Authorization", "90") // 假设授权令牌为"90"，需要根据实际情况替换
                        .accept(MediaType.APPLICATION_JSON)) // 指定接受的内容类型
                .andExpect(status().isOk()) // 期望的状态码为200
                .andExpect(content().string("true")); // 适用于控制器方法返回的是一个简单的布尔值true，并且Spring MVC将其转换为字符串形式的JSON响应。
    }
}
