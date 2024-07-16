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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
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
    void testUploadFileWithEmptyFile() throws Exception {
        // 使用空文件进行测试
        mockMvc.perform(multipart("/uploadFile")
                        .file(new MockMultipartFile("file", "", "application/octet-stream", new byte[0]))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("uploadFail"));
    }

    @Test
    public void testUploadFile_IOException() throws Exception {
        // 模拟一个文件上传
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test content".getBytes());

        // 模拟 Files.createDirectories 抛出 IOException
        try (var mockedStatic = mockStatic(Files.class)) {
            mockedStatic.when(() -> Files.createDirectories(any(Path.class)))
                    .thenThrow(new IOException("Simulated IO Exception"));

            mockMvc.perform(multipart("/uploadFile").file(file))
                    .andExpect(status().isOk())
                    .andExpect(content().string("uploadFail"));
        }
    }

    @Test
    public void testDeleteFile_FileExists() throws Exception {
        try (var mockedStatic = mockStatic(Files.class)) {
            Path mockPath = Paths.get("static/test.txt");

            mockedStatic.when(() -> Files.exists(mockPath)).thenReturn(true);
            mockedStatic.when(() -> Files.delete(mockPath)).thenAnswer(invocation -> null);

            mockMvc.perform(get("/deleteFile").param("fileName", "test.txt"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }
    }

    @Test
    public void testDeleteFile_FileNotExists() throws Exception {
        try (var mockedStatic = mockStatic(Files.class)) {
            Path mockPath = Paths.get("static/test.txt");

            mockedStatic.when(() -> Files.exists(mockPath)).thenReturn(false);

            mockMvc.perform(get("/deleteFile").param("fileName", "test.txt"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        }
    }

    @Test
    public void testDeleteFile_IOException() throws Exception {
        try (var mockedStatic = mockStatic(Files.class)) {
            Path mockPath = Paths.get("static/test.txt");

            mockedStatic.when(() -> Files.exists(mockPath)).thenReturn(true);
            mockedStatic.when(() -> Files.delete(mockPath)).thenThrow(new IOException("Simulated IO Exception"));

            mockMvc.perform(get("/deleteFile").param("fileName", "test.txt"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        }
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

    @Test
    public void testDeleteRecordVideoByRecordVideoID_Success() throws Exception {
        mockMvc.perform(get("/deleteRecordVideoByRecordVideoID")
                        .param("recordVideoID", "123"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(mockRecordVideoService).deleteByRecordVideoID(123);
    }

}
