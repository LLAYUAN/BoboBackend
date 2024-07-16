package org.example.recommendservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.RoomHotIndex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageConsumerTest {

    @InjectMocks
    private MessageConsumer messageConsumer;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RoomDao roomDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageConsumer.setMessageQueue(new ConcurrentLinkedQueue<>());
    }

    @Test
    void receiveMessage() throws JsonProcessingException {
        String jsonMessage = "{\"roomId\": 1, \"viewCount\": 10, \"likeCount\": 5, \"shareCount\": 2, \"consumptionCount\": 3, \"messageCount\": 4, \"newFollowerCount\": 1, \"sumViewTime\": 100}";
        AddHotIndex addHotIndex = new AddHotIndex(1, 10, 5, 2, 3, 4, 1, 100);
        when(objectMapper.readValue(jsonMessage, AddHotIndex.class)).thenReturn(addHotIndex);

        messageConsumer.receiveMessage(jsonMessage);

        assert !messageConsumer.getMessageQueue().isEmpty();
        assert messageConsumer.getMessageQueue().contains(addHotIndex);
    }

    @Test
    void processMessages() {
        AddHotIndex addHotIndex1 = new AddHotIndex(1, 10, 5, 2, 3, 4, 1, 100);
        AddHotIndex addHotIndex2 = new AddHotIndex(1, 5, 2, 1, 1, 1, 1, 50);
        messageConsumer.getMessageQueue().add(addHotIndex1);
        messageConsumer.getMessageQueue().add(addHotIndex2);

        RoomHotIndex roomHotIndex = new RoomHotIndex(1, 0, 10, 5, 2, 3, 4, 1, 100);
        when(roomDao.getRoomHotIndex(1)).thenReturn(roomHotIndex);

        messageConsumer.processMessages();

        verify(roomDao, times(1)).saveRoomHotIndex(any(RoomHotIndex.class));
    }
}
