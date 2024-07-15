package org.example.livevideoservice.controller;

import org.example.livevideoservice.entity.RoomInfo;
import org.example.livevideoservice.repository.RoomInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StreamingController.class)
class StreamingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomInfoRepository mockRoomInfoRepository;

    @Test
    void testGetCameraDevices() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/camera-devices")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testStartCameraStream() throws Exception {
        // Setup
        // Configure RoomInfoRepository.findByRoomID(...).
        final RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setRoomName("roomName");
        roomInfo.setDescription("description");
        roomInfo.setStatus(false);
        roomInfo.setCoverUrl("coverUrl");
        when(mockRoomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        // Run the test and verify the results
        mockMvc.perform(post("/camera-live")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));

        // Confirm RoomInfoRepository.save(...).
        final RoomInfo entity = new RoomInfo();
        entity.setRoomID(0);
        entity.setRoomName("roomName");
        entity.setDescription("description");
        entity.setStatus(false);
        entity.setCoverUrl("coverUrl");
        verify(mockRoomInfoRepository).save(entity);
    }

    @Test
    void testStartCameraStream_RoomInfoRepositoryFindByRoomIDReturnsNull() throws Exception {
        // Setup
        when(mockRoomInfoRepository.findByRoomID(0)).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(post("/camera-live")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testStartDesktopStream() throws Exception {
        // Setup
        // Configure RoomInfoRepository.findByRoomID(...).
        final RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setRoomName("roomName");
        roomInfo.setDescription("description");
        roomInfo.setStatus(false);
        roomInfo.setCoverUrl("coverUrl");
        when(mockRoomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        // Run the test and verify the results
        mockMvc.perform(post("/desktop-live")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));

        // Confirm RoomInfoRepository.save(...).
        final RoomInfo entity = new RoomInfo();
        entity.setRoomID(0);
        entity.setRoomName("roomName");
        entity.setDescription("description");
        entity.setStatus(false);
        entity.setCoverUrl("coverUrl");
        verify(mockRoomInfoRepository).save(entity);
    }

    @Test
    void testStartDesktopStream_RoomInfoRepositoryFindByRoomIDReturnsNull() throws Exception {
        // Setup
        when(mockRoomInfoRepository.findByRoomID(0)).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(post("/desktop-live")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testStopStream() throws Exception {
        // Setup
        // Configure RoomInfoRepository.findByRoomID(...).
        final RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setRoomName("roomName");
        roomInfo.setDescription("description");
        roomInfo.setStatus(false);
        roomInfo.setCoverUrl("coverUrl");
        when(mockRoomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        // Run the test and verify the results
        mockMvc.perform(post("/stop-stream")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));

        // Confirm RoomInfoRepository.save(...).
        final RoomInfo entity = new RoomInfo();
        entity.setRoomID(0);
        entity.setRoomName("roomName");
        entity.setDescription("description");
        entity.setStatus(false);
        entity.setCoverUrl("coverUrl");
        verify(mockRoomInfoRepository).save(entity);
    }

    @Test
    void testStopStream_RoomInfoRepositoryFindByRoomIDReturnsNull() throws Exception {
        // Setup
        when(mockRoomInfoRepository.findByRoomID(0)).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(post("/stop-stream")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testStartRecord() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(post("/record")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
