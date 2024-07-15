package org.example.messageservice.controller;

import org.example.messageservice.entity.ChatMessage;
import org.example.messageservice.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class HistoryControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private HistoryController historyController;

    private MockMvc mockMvc;

    private List<ChatMessage> sampleMessages;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build();

        ChatMessage msg1 = new ChatMessage();
        msg1.setId("1");
        msg1.setContent("Hello");
        msg1.setRoomID(1);
        msg1.setTimestamp(Instant.now());

        ChatMessage msg2 = new ChatMessage();
        msg2.setId("2");
        msg2.setContent("Hi");
        msg2.setRoomID(1);
        msg2.setTimestamp(Instant.now());

        sampleMessages = Arrays.asList(msg1, msg2);
    }

    @Test
    public void testGetHistoryMessages() throws Exception {
        Instant timestamp = Instant.now();

        when(chatService.getHistoryMessages(eq(1), any(Instant.class))).thenReturn(sampleMessages);

        mockMvc.perform(get("/history/1")
                        .param("timestamp", timestamp.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].content", is("Hello")))
                .andExpect(jsonPath("$[1].content", is("Hi")));
    }
}
