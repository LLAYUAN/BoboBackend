package org.example.recommendservice.Service;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.BrowsHistoryDao;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.*;
import org.example.recommendservice.utils.HotIndexCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserBrowsHistoryServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private BrowsHistoryDao browsHistoryDao;

    @Mock
    private RoomDao roomDao;

    @InjectMocks
    private UserBrowsHistoryService userBrowsHistoryService;

    private BrowsingRecord sampleRecord;
    private UserBrowsHistory sampleUserHistory;
    private RoomInfo sampleRoomInfo;

    @BeforeEach
    void setUp() {
        sampleRecord = new BrowsingRecord();
        sampleRecord.setRoomId(1);
        sampleRecord.setLikeCount(5);
        sampleRecord.setShareCount(2);
        sampleRecord.setConsumptionCount(1);
        sampleRecord.setMessageCount(3);
        sampleRecord.setFollowStatus(1);
        sampleRecord.setStartTime(LocalDateTime.now());
        sampleRecord.setWatchDuration(3600);

        sampleUserHistory = new UserBrowsHistory();
        sampleUserHistory.setUserId("111");
        sampleUserHistory.setBrowsingHistory(new ArrayList<>(List.of(sampleRecord)));

        sampleRoomInfo = new RoomInfo();
        sampleRoomInfo.setRoomID(1);
        sampleRoomInfo.setRoomName("Room1");
        sampleRoomInfo.setDescription("Description");
        sampleRoomInfo.setStatus(true);
        sampleRoomInfo.setCoverUrl("coverUrl");
        sampleRoomInfo.setStudy(false);
        sampleRoomInfo.setEntertain(true);
        sampleRoomInfo.setOther(false);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(111);
        sampleRoomInfo.setUserInfo(userInfo);
    }

    @Test
    void testAddBrowsingRecord() {
        when(browsHistoryDao.getUserBrowsHistory("111")).thenReturn(sampleUserHistory);

        userBrowsHistoryService.addBrowsingRecord("111", sampleRecord);

        verify(browsHistoryDao, times(1)).saveUserBrowsHistory(any(UserBrowsHistory.class));
    }

    @Test
    void testRecommendRooms() {
        RoomHotIndex roomHotIndex = new RoomHotIndex();
        roomHotIndex.setRoomId(1);
        roomHotIndex.setDuration(100);
        roomHotIndex.setViewCount(50);
        roomHotIndex.setLikeCount(5);
        roomHotIndex.setShareCount(2);
        roomHotIndex.setConsumptionCount(1);
        roomHotIndex.setMessageCount(3);
        roomHotIndex.setNewFollowerCount(10);
        roomHotIndex.setSumViewTime(5000);

        when(roomDao.findAll()).thenReturn(List.of(sampleRoomInfo));
        when(mongoTemplate.findOne(any(Query.class), eq(RoomHotIndex.class))).thenReturn(roomHotIndex);
        when(roomDao.findById(1)).thenReturn(sampleRoomInfo);

        List<RoomCardInfo> recommendedRooms = userBrowsHistoryService.recommendRooms("111");

        assertFalse(recommendedRooms.isEmpty());
        assertEquals(1, recommendedRooms.get(0).getId());
    }
}
