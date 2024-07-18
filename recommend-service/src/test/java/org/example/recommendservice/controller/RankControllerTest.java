package org.example.recommendservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Service.RoomInfoService;
import org.example.recommendservice.Service.UserBrowsHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RankController.class)
public class RankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomInfoService roomInfoService;

    @MockBean
    private UserBrowsHistoryService userBrowsHistoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetRank() throws Exception {
        // Mock data
        RoomCardInfo room1 = new RoomCardInfo();
        room1.setId(1);
        room1.setRoomName("Room 1");
        room1.setDescription("Description 1");
        room1.setCoverUrl("cover1.jpg");
        room1.setUserName("User1");
        room1.setUserDescription("User1's room");
        room1.setAvatarUrl("avatar1.jpg");
        room1.setTags(Arrays.asList(true, false, true));
        room1.setHotIndex(10);

        RoomCardInfo room2 = new RoomCardInfo();
        room2.setId(2);
        room2.setRoomName("Room 2");
        room2.setDescription("Description 2");
        room2.setCoverUrl("cover2.jpg");
        room2.setUserName("User2");
        room2.setUserDescription("User2's room");
        room2.setAvatarUrl("avatar2.jpg");
        room2.setTags(Arrays.asList(false, true, false));
        room2.setHotIndex(20);

        List<RoomCardInfo> roomList = Arrays.asList(room1, room2);

        // Mock service method
        when(roomInfoService.getRank(1)).thenReturn(roomList);

        // Perform GET request
        mockMvc.perform(get("/rank/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].roomName").value("Room 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].coverUrl").value("cover1.jpg"))
                .andExpect(jsonPath("$[0].userName").value("User1"))
                .andExpect(jsonPath("$[0].userDescription").value("User1's room"))
                .andExpect(jsonPath("$[0].avatarUrl").value("avatar1.jpg"))
                .andExpect(jsonPath("$[0].tags").isArray())
                .andExpect(jsonPath("$[0].tags[0]").value(true))
                .andExpect(jsonPath("$[0].tags[1]").value(false))
                .andExpect(jsonPath("$[0].tags[2]").value(true))
                .andExpect(jsonPath("$[0].hotIndex").value(10))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].roomName").value("Room 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].coverUrl").value("cover2.jpg"))
                .andExpect(jsonPath("$[1].userName").value("User2"))
                .andExpect(jsonPath("$[1].userDescription").value("User2's room"))
                .andExpect(jsonPath("$[1].avatarUrl").value("avatar2.jpg"))
                .andExpect(jsonPath("$[1].tags").isArray())
                .andExpect(jsonPath("$[1].tags[0]").value(false))
                .andExpect(jsonPath("$[1].tags[1]").value(true))
                .andExpect(jsonPath("$[1].tags[2]").value(false))
                .andExpect(jsonPath("$[1].hotIndex").value(20));
    }

    @Test
    public void testRecommendRooms() throws Exception {
        // Mock data
        RoomCardInfo room1 = new RoomCardInfo();
        room1.setId(1);
        room1.setRoomName("Recommended Room 1");
        room1.setDescription("Recommended Description 1");
        room1.setCoverUrl("recommend1.jpg");
        room1.setUserName("User1");
        room1.setUserDescription("User1's recommended room");
        room1.setAvatarUrl("avatar1.jpg");
        room1.setTags(Arrays.asList(true, false, true));
        room1.setHotIndex(15);

        RoomCardInfo room2 = new RoomCardInfo();
        room2.setId(2);
        room2.setRoomName("Recommended Room 2");
        room2.setDescription("Recommended Description 2");
        room2.setCoverUrl("recommend2.jpg");
        room2.setUserName("User2");
        room2.setUserDescription("User2's recommended room");
        room2.setAvatarUrl("avatar2.jpg");
        room2.setTags(Arrays.asList(false, true, false));
        room2.setHotIndex(25);

        RoomCardInfo room3 = new RoomCardInfo();
        room3.setId(3);
        room3.setRoomName("Ranked Room 1");
        room3.setDescription("Ranked Description 1");
        room3.setCoverUrl("rank1.jpg");
        room3.setUserName("User3");
        room3.setUserDescription("User3's ranked room");
        room3.setAvatarUrl("avatar3.jpg");
        room3.setTags(Arrays.asList(true, true, false));
        room3.setHotIndex(30);

        List<RoomCardInfo> recommendedRooms = Arrays.asList(room1, room2);
        List<RoomCardInfo> rankedRooms = Arrays.asList(room3);

        // Mock service methods
        when(userBrowsHistoryService.recommendRooms("user123")).thenReturn(recommendedRooms);
        when(roomInfoService.getRank(-1)).thenReturn(rankedRooms);

        // Perform GET request with Authorization header
        mockMvc.perform(get("/rank/recommend")
                        .header("Authorization", "user123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].roomName").value("Recommended Room 1"))
                .andExpect(jsonPath("$[0].description").value("Recommended Description 1"))
                .andExpect(jsonPath("$[0].coverUrl").value("recommend1.jpg"))
                .andExpect(jsonPath("$[0].userName").value("User1"))
                .andExpect(jsonPath("$[0].userDescription").value("User1's recommended room"))
                .andExpect(jsonPath("$[0].avatarUrl").value("avatar1.jpg"))
                .andExpect(jsonPath("$[0].tags").isArray())
                .andExpect(jsonPath("$[0].tags[0]").value(true))
                .andExpect(jsonPath("$[0].tags[1]").value(false))
                .andExpect(jsonPath("$[0].tags[2]").value(true))
                .andExpect(jsonPath("$[0].hotIndex").value(15))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].roomName").value("Recommended Room 2"))
                .andExpect(jsonPath("$[1].description").value("Recommended Description 2"))
                .andExpect(jsonPath("$[1].coverUrl").value("recommend2.jpg"))
                .andExpect(jsonPath("$[1].userName").value("User2"))
                .andExpect(jsonPath("$[1].userDescription").value("User2's recommended room"))
                .andExpect(jsonPath("$[1].avatarUrl").value("avatar2.jpg"))
                .andExpect(jsonPath("$[1].tags").isArray())
                .andExpect(jsonPath("$[1].tags[0]").value(false))
                .andExpect(jsonPath("$[1].tags[1]").value(true))
                .andExpect(jsonPath("$[1].tags[2]").value(false))
                .andExpect(jsonPath("$[1].hotIndex").value(25))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].roomName").value("Ranked Room 1"))
                .andExpect(jsonPath("$[2].description").value("Ranked Description 1"))
                .andExpect(jsonPath("$[2].coverUrl").value("rank1.jpg"))
                .andExpect(jsonPath("$[2].userName").value("User3"))
                .andExpect(jsonPath("$[2].userDescription").value("User3's ranked room"))
                .andExpect(jsonPath("$[2].userDescription").value("User3's ranked room"))
                .andExpect(jsonPath("$[2].avatarUrl").value("avatar3.jpg"))
                .andExpect(jsonPath("$[2].tags").isArray())
                .andExpect(jsonPath("$[2].tags[0]").value(true))
                .andExpect(jsonPath("$[2].tags[1]").value(true))
                .andExpect(jsonPath("$[2].tags[2]").value(false))
                .andExpect(jsonPath("$[2].hotIndex").value(30));
    }
}
