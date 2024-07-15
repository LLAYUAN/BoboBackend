package org.example.livevideoservice.controller;

import org.example.livevideoservice.entity.UserActivity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserActivityController.class)
class UserActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MongoTemplate mockMongoTemplate;

    @Test
    void testUserEnter() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(post("/user-enter")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockMongoTemplate).save(new UserActivity("id", "userId", "roomId", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
    }

    @Test
    void testUserExit() throws Exception {
        // Setup
        // Configure MongoTemplate.find(...).
        final List<UserActivity> userActivities = List.of(
                new UserActivity("id", "userId", "roomId", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockMongoTemplate.find(new Query(null), UserActivity.class)).thenReturn(userActivities);

        // Run the test and verify the results
        mockMvc.perform(post("/user-exit")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockMongoTemplate).save(new UserActivity("id", "userId", "roomId", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
    }

    @Test
    void testUserExit_MongoTemplateFindReturnsNoItems() throws Exception {
        // Setup
        when(mockMongoTemplate.find(new Query(null), UserActivity.class)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(post("/user-exit")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetActiveUsers() throws Exception {
        // Setup
        // Configure MongoTemplate.find(...).
        final List<UserActivity> userActivities = List.of(
                new UserActivity("id", "userId", "roomId", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockMongoTemplate.find(new Query(null), UserActivity.class)).thenReturn(userActivities);

        // Run the test and verify the results
        mockMvc.perform(get("/active-users/{roomId}", "roomId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetActiveUsers_MongoTemplateReturnsNoItems() throws Exception {
        // Setup
        when(mockMongoTemplate.find(new Query(null), UserActivity.class)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/active-users/{roomId}", "roomId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetUserId() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/getUserId")
                        .header("Authorization", "authorizationHeader")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
