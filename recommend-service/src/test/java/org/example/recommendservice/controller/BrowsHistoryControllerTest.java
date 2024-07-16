package org.example.recommendservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.recommendservice.Service.UserBrowsHistoryService;
import org.example.recommendservice.entity.BrowsingRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrowsHistoryController.class)
public class BrowsHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBrowsHistoryService userBrowsHistoryService;

    @Test
    public void testAddBrowsingRecord() throws Exception {
        BrowsingRecord browsingRecord = new BrowsingRecord(666666, 1, 2, 30, 40, 1, LocalDateTime.now(), 1000L); // 示例中提供了所有字段

        String userId = "user123";

        doNothing().when(userBrowsHistoryService).addBrowsingRecord(userId, browsingRecord);

        mockMvc.perform(post("/history/add")
                        .header("Authorization", userId)
                        .content(asJsonString(browsingRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSetBrowsingRecord() throws Exception {
        BrowsingRecord browsingRecord = new BrowsingRecord(666666, 1, 2, 30, 40, 1, LocalDateTime.now(), 1000L); // 示例中提供了所有字段

        List<BrowsingRecord> browsingHistory = Collections.singletonList(browsingRecord);
        String userId = "user123";

        doNothing().when(userBrowsHistoryService).setBrowsingRecord(userId, browsingHistory);

        mockMvc.perform(post("/history/set")
                        .header("Authorization", userId)
                        .content(asJsonString(browsingHistory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Utility method to convert object to JSON string
    private String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // 注册 Java 8 时间模块
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
