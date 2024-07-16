package org.example.recommendservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Service.RoomInfoService;
import org.example.recommendservice.entity.RoomHotIndex;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(RoomInfoController.class)
class RoomInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomInfoService roomInfoService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void testAddRoomHotIndex() throws Exception {
        AddHotIndex addHotIndex = new AddHotIndex(1, 100, 50, 25, 10, 5, 2, 500);
        String json = new ObjectMapper().writeValueAsString(addHotIndex);

        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString());

        mockMvc.perform(post("/roomInfo/addRoomHotIndex")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(rabbitTemplate, times(1)).convertAndSend(eq("addHotIndexQueue"), eq(json));
    }

    @Test
    void testSaveRoomHotIndex() throws Exception {
        RoomHotIndex roomHotIndex = new RoomHotIndex();
        roomHotIndex.setRoomId(1);
        List<RoomHotIndex> roomHotIndexList = Collections.singletonList(roomHotIndex);
        String json = new ObjectMapper().writeValueAsString(roomHotIndexList);

        doNothing().when(roomInfoService).saveRoomHotIndexList(anyList());

        mockMvc.perform(post("/roomInfo/saveRoomHotIndex")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(roomInfoService, times(1)).saveRoomHotIndexList(roomHotIndexList);
    }

    @Test
    void testGetRank() throws Exception {
        int id = 1;
        RoomCardInfo roomCardInfo = new RoomCardInfo();
        roomCardInfo.setId(id);

        when(roomInfoService.getRoomInfo(anyInt())).thenReturn(roomCardInfo);

        mockMvc.perform(get("/roomInfo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(roomInfoService, times(1)).getRoomInfo(id);
    }
}
