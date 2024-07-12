package org.example.userservice.controller;

import org.example.userservice.Feign.Feign;
import org.example.userservice.dto.BasicUserDTO;
import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.service.UserInfoService;
import org.example.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService mockUserInfoService;
    @MockBean
    private UserService mockUserService;
    @MockBean
    private Feign mockFeign;

    @Test
    void testPersonalProfile() throws Exception {
        // Setup
        // Configure UserInfoService.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockUserInfoService.findUserInfoByUserID(0)).thenReturn(userInfo);

        when(mockUserService.getFollowees(0)).thenReturn(List.of(new BasicUserDTO(0, "nickname")));
        when(mockUserService.getFollowers(0)).thenReturn(List.of(new BasicUserDTO(0, "nickname")));

        // Run the test and verify the results
        mockMvc.perform(get("/personalProfile")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testPersonalProfile_UserServiceGetFolloweesReturnsNoItems() throws Exception {
        // Setup
        // Configure UserInfoService.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockUserInfoService.findUserInfoByUserID(0)).thenReturn(userInfo);

        when(mockUserService.getFollowees(0)).thenReturn(Collections.emptyList());
        when(mockUserService.getFollowers(0)).thenReturn(List.of(new BasicUserDTO(0, "nickname")));

        // Run the test and verify the results
        mockMvc.perform(get("/personalProfile")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testPersonalProfile_UserServiceGetFollowersReturnsNoItems() throws Exception {
        // Setup
        // Configure UserInfoService.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockUserInfoService.findUserInfoByUserID(0)).thenReturn(userInfo);

        when(mockUserService.getFollowees(0)).thenReturn(List.of(new BasicUserDTO(0, "nickname")));
        when(mockUserService.getFollowers(0)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/personalProfile")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetUserInfo() throws Exception {
        // Setup
        // Configure UserInfoService.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockUserInfoService.findUserInfoByUserID(0)).thenReturn(userInfo);

        // Run the test and verify the results
        mockMvc.perform(get("/getUserInfo")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetFollowees() throws Exception {
        // Setup
        when(mockUserService.getFollowees(0)).thenReturn(List.of(new BasicUserDTO(0, "nickname")));

        // Run the test and verify the results
        mockMvc.perform(get("/getFollowees")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetFollowees_UserServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockUserService.getFollowees(0)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/getFollowees")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetFollowers() throws Exception {
        // Setup
        when(mockUserService.getFollowers(0)).thenReturn(List.of(new BasicUserDTO(0, "nickname")));

        // Run the test and verify the results
        mockMvc.perform(get("/getFollowers")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetFollowers_UserServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockUserService.getFollowers(0)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/getFollowers")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testFollow() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(post("/follow")
                        .header("Authorization", "authorizationHeader")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockUserService).follow(0, 0);
    }

    @Test
    void testUnfollow() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(post("/unfollow")
                        .header("Authorization", "authorizationHeader")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockUserService).unfollow(0, 0);
    }

    @Test
    void testCheckIsFan() throws Exception {
        // Setup
        when(mockUserService.checkIsFan(0, 0)).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(get("/checkIsFan")
                        .header("Authorization", "authorizationHeader")
                        .param("followeeID", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testUpdateUserInfo() throws Exception {
        // Setup
        // Configure UserInfoService.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockUserInfoService.findUserInfoByUserID(0)).thenReturn(userInfo);

        // Run the test and verify the results
        mockMvc.perform(post("/updateUserInfo")
                        .header("Authorization", "authorizationHeader")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockUserInfoService).save(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1))));
    }

    @Test
    void testGetRoomInfo() throws Exception {
        // Setup
        // Configure UserInfoService.getRoomInfoByUserID(...).
        final RoomInfo roomInfo = new RoomInfo("roomName", "description", "coverUrl");
        when(mockUserInfoService.getRoomInfoByUserID(0)).thenReturn(roomInfo);

        // Run the test and verify the results
        mockMvc.perform(get("/getRoomInfo")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testVisitInfo() throws Exception {
        // Setup
        // Configure UserInfoService.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockUserInfoService.findUserInfoByUserID(0)).thenReturn(userInfo);

        when(mockUserService.getFollowerCount(0)).thenReturn(0);
        when(mockUserService.getFolloweeCount(0)).thenReturn(0);
        when(mockUserService.checkIsFan(0, 0)).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(post("/visitInfo")
                        .header("Authorization", "authorizationHeader")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testCreateRoom() throws Exception {
        // Setup
        when(mockUserService.createRoom(0, "roomName", "coverUrl", List.of(0))).thenReturn(0);

        // Run the test and verify the results
        mockMvc.perform(post("/createRoom")
                        .header("Authorization", "authorizationHeader")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testModifyPassword() throws Exception {
        // Setup
        when(mockUserInfoService.modifyPassword(0, "oldPassword", "newPassword")).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(post("/modifyPassword")
                        .header("Authorization", "authorizationHeader")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
