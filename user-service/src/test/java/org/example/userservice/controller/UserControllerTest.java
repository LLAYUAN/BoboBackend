//package org.example.userservice.controller;
//
//import org.example.userservice.Feign.Feign;
//import org.example.userservice.dto.BasicUserDTO;
//import org.example.userservice.dto.UserInfoDTO;
//import org.example.userservice.entity.RoomInfo;
//import org.example.userservice.entity.UserInfo;
//import org.example.userservice.service.UserInfoService;
//import org.example.userservice.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserInfoService mockUserInfoService;
//
//    @MockBean
//    private UserService mockUserService;
//
//    @MockBean
//    private Feign mockFeign;
//
//    @Test
//    void testPersonalProfile() throws Exception {
//        // Setup
//        final int userId = 1;
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockUserInfoService.findUserInfoByUserID(userId)).thenReturn(userInfo);
//
//        final List<BasicUserDTO> followees = Collections.singletonList(new BasicUserDTO(2, "follower"));
//        final List<BasicUserDTO> followers = Collections.singletonList(new BasicUserDTO(3, "follower"));
//
//        when(mockUserService.getFollowees(userId)).thenReturn(followees);
//        when(mockUserService.getFollowers(userId)).thenReturn(followers);
//
//        // Perform and verify
//        mockMvc.perform(get("/personalProfile")
//                        .header("Authorization", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data.user.email").value("email"))
//                .andExpect(jsonPath("$.data.following[0].userID").value(2))
//                .andExpect(jsonPath("$.data.followers[0].userID").value(3));
//    }
//
//    @Test
//    void testPersonalProfile_NoFollowees() throws Exception {
//        // Setup
//        final int userId = 1;
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockUserInfoService.findUserInfoByUserID(userId)).thenReturn(userInfo);
//
//        final List<BasicUserDTO> followers = Collections.singletonList(new BasicUserDTO(3, "follower"));
//
//        when(mockUserService.getFollowees(userId)).thenReturn(Collections.emptyList());
//        when(mockUserService.getFollowers(userId)).thenReturn(followers);
//
//        // Perform and verify
//        mockMvc.perform(get("/personalProfile")
//                        .header("Authorization", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data.user.email").value("email"))
//                .andExpect(jsonPath("$.data.following").isArray())
//                .andExpect(jsonPath("$.data.following").isEmpty())
//                .andExpect(jsonPath("$.data.followers[0].userID").value(3));
//    }
//
//    @Test
//    void testPersonalProfile_NoFollowers() throws Exception {
//        // Setup
//        final int userId = 1;
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockUserInfoService.findUserInfoByUserID(userId)).thenReturn(userInfo);
//
//        final List<BasicUserDTO> followees = Collections.singletonList(new BasicUserDTO(2, "follower"));
//
//        when(mockUserService.getFollowees(userId)).thenReturn(followees);
//        when(mockUserService.getFollowers(userId)).thenReturn(Collections.emptyList());
//
//        // Perform and verify
//        mockMvc.perform(get("/personalProfile")
//                        .header("Authorization", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data.user.email").value("email"))
//                .andExpect(jsonPath("$.data.following[0].userID").value(2))
//                .andExpect(jsonPath("$.data.followers").isArray())
//                .andExpect(jsonPath("$.data.followers").isEmpty());
//    }
//
//    @Test
//    void testGetUserInfo() throws Exception {
//        // Setup
//        final int userId = 1;
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockUserInfoService.findUserInfoByUserID(userId)).thenReturn(userInfo);
//
//        // Perform and verify
//        mockMvc.perform(get("/getUserInfo")
//                        .header("Authorization", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data.email").value("email"))
//                .andExpect(jsonPath("$.data.nickname").value("nickname"));
//    }
//
//    @Test
//    void testGetFollowees() throws Exception {
//        // Setup
//        final int userId = 1;
//        final List<BasicUserDTO> followees = Collections.singletonList(new BasicUserDTO(2, "follower"));
//        when(mockUserService.getFollowees(userId)).thenReturn(followees);
//
//        // Perform and verify
//        mockMvc.perform(get("/getFollowees")
//                        .header("Authorization", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data[0].userID").value(2))
//                .andExpect(jsonPath("$.data[0].nickname").value("follower"));
//    }
//
//    @Test
//    void testGetFollowers() throws Exception {
//        // Setup
//        final int userId = 1;
//        final List<BasicUserDTO> followers = Collections.singletonList(new BasicUserDTO(3, "follower"));
//        when(mockUserService.getFollowers(userId)).thenReturn(followers);
//
//        // Perform and verify
//        mockMvc.perform(get("/getFollowers")
//                        .header("Authorization", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data[0].userID").value(3))
//                .andExpect(jsonPath("$.data[0].nickname").value("follower"));
//    }
//
//    @Test
//    void testFollow() throws Exception {
//        // Setup
//        final int followerId = 1;
//        final int followeeId = 2;
//
//        // Perform and verify
//        mockMvc.perform(post("/follow")
//                        .header("Authorization", "1")
//                        .content("{\"followeeID\": 2}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data").value("Follow successfully"));
//
//        verify(mockUserService).follow(followeeId, followerId);
//    }
//
//    @Test
//    void testUnfollow() throws Exception {
//        // Setup
//        final int followerId = 1;
//        final int followeeId = 2;
//
//        // Perform and verify
//        mockMvc.perform(post("/unfollow")
//                        .header("Authorization", "1")
//                        .content("{\"followeeID\": 2}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data").value("Unfollow successfully"));
//
//        verify(mockUserService).unfollow(followeeId, followerId);
//    }
//
//    @Test
//    void testCheckIsFan() throws Exception {
//        // Setup
//        final int followerId = 1;
//        final int followeeId = 2;
//        when(mockUserService.checkIsFan(followeeId, followerId)).thenReturn(true);
//
//        // Perform and verify
//        mockMvc.perform(get("/checkIsFan")
//                        .header("Authorization", "1")
//                        .param("followeeID", "2")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data").value(true));
//    }
//
//    @Test
//    void testUpdateUserInfo() throws Exception {
//        // Setup
//        final int userId = 1;
//        final UserInfoDTO userInfoDTO = new UserInfoDTO(1, "nickname", "avatarUrl");
//
//        // Mock findUserInfoByUserID
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserID(userId); // Set the userID
//        when(mockUserInfoService.findUserInfoByUserID(userId)).thenReturn(userInfo);
//
//        // Perform and verify
//        mockMvc.perform(post("/updateUserInfo")
//                        .header("Authorization", "1")
//                        .content("{\"nickname\": \"nickname\", \"avatarUrl\": \"avatarUrl\"}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200));
//
//        // Verify save method is called with the correct UserInfo object
//        verify(mockUserInfoService).save(userInfo);
//    }
//
//
//    @Test
//    void testCreateRoom() throws Exception {
//        // Setup
//        final int userId = 1;
//        when(mockUserService.createRoom(userId, "roomName", "coverUrl", Collections.singletonList(0))).thenReturn(0);
//
//        // Perform and verify
//        mockMvc.perform(post("/createRoom")
//                        .header("Authorization", "1")
//                        .content("{\"roomName\": \"roomName\", \"coverUrl\": \"coverUrl\", \"userIDs\": [0]}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data").value(0));
//    }
//
//    @Test
//    void testGetRoomInfo() throws Exception {
//        // Setup
//        final int userId = 1;
//        final RoomInfo roomInfo = new RoomInfo(); // Mock roomInfo as needed
//        roomInfo.setStudy(false);
//        roomInfo.setEntertain(false);
//        roomInfo.setOther(false);
//        final UserInfo userInfo = new UserInfo();
//        userInfo.setUserID(1);
//        roomInfo.setUserInfo(userInfo);
//        when(mockUserInfoService.getRoomInfoByUserID(userId)).thenReturn(roomInfo);
//
//        // Perform and verify
//        mockMvc.perform(get("/getRoomInfo")
//                        .header("Authorization", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                // Add assertions for roomInfo DTO fields as needed
//                .andExpect(jsonPath("$.data").exists());
//    }
//
//    @Test
//    void testVisitInfo() throws Exception {
//        // Setup
//        final int userId = 1;
//        final int visitorId = 2;
//        final Map<String, Integer> requestBody = Collections.singletonMap("userID", userId);
//        final UserInfo userInfo = new UserInfo(); // Mock userInfo as needed
//        final int followerCount = 1; // Mock followerCount
//        final int followeeCount = 2; // Mock followeeCount
//        final boolean isFan = true; // Mock isFan
//        when(mockUserService.checkIsFan(userId, visitorId)).thenReturn(isFan);
//        when(mockUserInfoService.findUserInfoByUserID(userId)).thenReturn(userInfo);
//        when(mockUserService.getFollowerCount(userId)).thenReturn(followerCount);
//        when(mockUserService.getFolloweeCount(userId)).thenReturn(followeeCount);
//
//        // Perform and verify
//        mockMvc.perform(post("/visitInfo")
//                        .header("Authorization", "1")
//                        .content("{\"userID\": 1}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                // Add assertions for userInfoDTO fields as needed
//                .andExpect(jsonPath("$.data").exists());
//    }
//
//    @Test
//    void testModifyPassword() throws Exception {
//        // Setup
//        final int userId = 1;
//        final String oldPassword = "oldPassword";
//        final String newPassword = "newPassword";
//        final Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("oldPassword", oldPassword);
//        requestBody.put("newPassword", newPassword);
//        when(mockUserInfoService.modifyPassword(userId, oldPassword, newPassword)).thenReturn(true);
//
//        // Perform and verify
//        mockMvc.perform(post("/modifyPassword")
//                        .header("Authorization", "1")
//                        .content("{\"oldPassword\": \"oldPassword\", \"newPassword\": \"newPassword\"}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.data").value("修改密码成功"));
//    }
//
//    @Test
//    void testModifyPassword_Failure() throws Exception {
//        // Setup
//        final int userId = 1;
//        final String oldPassword = "oldPassword";
//        final String newPassword = "newPassword";
//        final Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("oldPassword", oldPassword);
//        requestBody.put("newPassword", newPassword);
//        when(mockUserInfoService.modifyPassword(userId, oldPassword, newPassword)).thenReturn(false);
//
//        // Perform and verify
//        mockMvc.perform(post("/modifyPassword")
//                        .header("Authorization", "1")
//                        .content("{\"oldPassword\": \"oldPassword\", \"newPassword\": \"newPassword\"}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(500)) // Assuming failure returns 500
//                .andExpect(jsonPath("$.message").value("修改密码失败"));
//    }
//
//
////
////    @Test
////    void testSaveRoomHotIndex() throws Exception {
////        // Setup
////        final int userId = 1;
////
////        // Perform and verify
////        mockMvc.perform(post("/saveRoomHotIndex")
////                        .header("Authorization", "1")
////                        .content("100")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.code").value(200));
////
////        verify(mockUserService).saveRoomHotIndex(userId, 100);
////    }
//}
