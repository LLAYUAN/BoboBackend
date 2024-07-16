package org.example.livevideoservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.livevideoservice.entity.Result;
import org.example.livevideoservice.entity.UserActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserActivityController.class)
class UserActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Set up Jackson ObjectMapper if needed
        // objectMapper.registerModule(new JavaTimeModule());
        // objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Initialize mockMvc with standalone setup if necessary
        // mockMvc = MockMvcBuilders.standaloneSetup(new UserActivityController()).build();
    }

    @Test
    void testUserEnter_NewEntry() throws Exception {
        // Setup
        String userId = "testUser";
        String roomId = "testRoom";
        Map<String, String> payload = Map.of("userId", userId, "roomId", roomId);

        // Mock the query result to return an empty list
        when(mongoTemplate.find(any(Query.class), eq(UserActivity.class))).thenReturn(Collections.emptyList());

        // Expected response
        String expectedResponse = objectMapper.writeValueAsString(Result.success());

        // Run the test and verify the results
        ResultActions resultActions = mockMvc.perform(post("/user-enter")
                        .content(objectMapper.writeValueAsString(payload))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));

        // Verify that the save method was called
        verify(mongoTemplate, times(1)).save(any(UserActivity.class));
    }

    @Test
    void testUserEnter_ExistingEntry() throws Exception {
        // Setup
        String userId = "testUser";
        String roomId = "testRoom";
        Map<String, String> payload = Map.of("userId", userId, "roomId", roomId);

        // Mock the query result to return a non-empty list
        UserActivity existingActivity = new UserActivity();
        existingActivity.setUserId(userId);
        existingActivity.setRoomId(roomId);
        existingActivity.setEnterTime(LocalDateTime.now());
        existingActivity.setExitTime(null);
        when(mongoTemplate.find(any(Query.class), eq(UserActivity.class))).thenReturn(List.of(existingActivity));

        // Expected response
        String expectedResponse = objectMapper.writeValueAsString(Result.success());

        // Run the test and verify the results
        ResultActions resultActions = mockMvc.perform(post("/user-enter")
                        .content(objectMapper.writeValueAsString(payload))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));

        // Verify that the save method was not called
        verify(mongoTemplate, times(0)).save(any(UserActivity.class));
    }

    @Test
    void testUserExit() throws Exception {
        // Setup
        String userId = "testUser";
        String roomId = "testRoom";
        Map<String, String> payload = Map.of("userId", userId, "roomId", roomId);

        // Mock the query result to return a non-empty list
        UserActivity existingActivity = new UserActivity();
        existingActivity.setUserId(userId);
        existingActivity.setRoomId(roomId);
        existingActivity.setEnterTime(LocalDateTime.now());
        existingActivity.setExitTime(null);
        when(mongoTemplate.find(any(Query.class), eq(UserActivity.class))).thenReturn(List.of(existingActivity));

        // Expected response
        String expectedResponse = objectMapper.writeValueAsString(Result.success("User exited"));

        // Run the test and verify the results
        ResultActions resultActions = mockMvc.perform(post("/user-exit")
                        .content(objectMapper.writeValueAsString(payload))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));

        // Verify that the save method was called with the updated exit time
        verify(mongoTemplate, times(1)).save(existingActivity);
    }

    @Test
    void testGetActiveUsers() throws Exception {
        // Setup
        String roomId = "testRoom";

        // Mock the query result to return a list of active users
        UserActivity activeUser = new UserActivity();
        activeUser.setUserId("testUser");
        activeUser.setRoomId(roomId);
        activeUser.setEnterTime(LocalDateTime.now());
        activeUser.setExitTime(null);
        when(mongoTemplate.find(any(Query.class), eq(UserActivity.class))).thenReturn(List.of(activeUser));

        // Expected response
        String expectedResponse = objectMapper.writeValueAsString(Result.success(List.of(activeUser)));

        // Run the test and verify the results
        ResultActions resultActions = mockMvc.perform(get("/active-users/" + roomId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void testGetUserId() throws Exception {
        // Setup
        String authorizationHeader = "Bearer testToken";

        // Expected response
        String expectedResponse = objectMapper.writeValueAsString(Result.success(authorizationHeader));

        // Run the test and verify the results
        ResultActions resultActions = mockMvc.perform(get("/getUserId")
                        .header("Authorization", authorizationHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }
}
