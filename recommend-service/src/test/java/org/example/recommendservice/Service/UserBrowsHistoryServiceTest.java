package org.example.recommendservice.Service;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.BrowsHistoryDao;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.*;
import org.example.recommendservice.utils.HotIndexCalculator;
import org.example.recommendservice.utils.SimilarIndexCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        // 设置示例浏览记录
        sampleRecord = new BrowsingRecord();
        sampleRecord.setRoomId(1);
        sampleRecord.setLikeCount(5);
        sampleRecord.setShareCount(2);
        sampleRecord.setConsumptionCount(1);
        sampleRecord.setMessageCount(3);
        sampleRecord.setFollowStatus(1);
        sampleRecord.setStartTime(LocalDateTime.now());
        sampleRecord.setWatchDuration(3600);

        // 设置示例用户浏览历史
        sampleUserHistory = new UserBrowsHistory();
        sampleUserHistory.setUserId("111");
        sampleUserHistory.setBrowsingHistory(new ArrayList<>(List.of(sampleRecord)));

        // 设置示例房间信息
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
        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("111")).thenReturn(sampleUserHistory);

        // 调用服务方法
        userBrowsHistoryService.addBrowsingRecord("111", sampleRecord);

        // 验证保存方法是否被调用一次
        verify(browsHistoryDao, times(1)).saveUserBrowsHistory(any(UserBrowsHistory.class));

        // 验证添加后的浏览记录
        UserBrowsHistory updatedUserHistory = browsHistoryDao.getUserBrowsHistory("111");
        assertNotNull(updatedUserHistory);
        List<BrowsingRecord> updatedRecords = updatedUserHistory.getBrowsingHistory();
        assertFalse(updatedRecords.isEmpty());
        assertEquals(2, updatedRecords.size()); // 确保添加成功
        assertEquals(sampleRecord, updatedRecords.get(0)); // 确保按时间排序
    }

    @Test
    void testSetBrowsingRecord() {
        // 准备新的浏览记录
        List<BrowsingRecord> newHistory = new ArrayList<>(List.of(sampleRecord));

        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("111")).thenReturn(sampleUserHistory);

        // 调用服务方法
        userBrowsHistoryService.setBrowsingRecord("111", newHistory);

        // 验证保存方法是否被调用一次
        verify(browsHistoryDao, times(1)).saveUserBrowsHistory(any(UserBrowsHistory.class));

        // 验证设置后的浏览记录
        UserBrowsHistory updatedUserHistory = browsHistoryDao.getUserBrowsHistory("111");
        assertNotNull(updatedUserHistory);
        List<BrowsingRecord> updatedRecords = updatedUserHistory.getBrowsingHistory();
        assertFalse(updatedRecords.isEmpty());
        assertEquals(1, updatedRecords.size()); // 确保设置成功
        assertEquals(sampleRecord, updatedRecords.get(0)); // 确保设置正确
    }

    @Test
    void testRecommendRooms() {
        // 准备房间热度索引数据
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

        // 模拟房间Dao返回数据
        when(roomDao.findAll()).thenReturn(List.of(sampleRoomInfo));
        when(mongoTemplate.findOne(any(Query.class), eq(RoomHotIndex.class))).thenReturn(roomHotIndex);
        when(roomDao.findById(1)).thenReturn(sampleRoomInfo);

        // 调用推荐方法
        List<RoomCardInfo> recommendedRooms = userBrowsHistoryService.recommendRooms("111");

        // 验证推荐结果
        assertFalse(recommendedRooms.isEmpty());
        assertEquals(1, recommendedRooms.size());
        assertEquals(1, recommendedRooms.get(0).getId()); // 确保推荐的是期望的房间
    }

    @Test
    void testGetCategoryFromRecord() {
        // 模拟房间Dao返回数据
        when(roomDao.findById(1)).thenReturn(sampleRoomInfo);

        // 调用方法获取分类信息
        List<Boolean> categories = userBrowsHistoryService.getCategoryFromRecord(sampleRecord);

        // 验证分类信息
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals(3, categories.size());
        assertFalse(categories.get(0));
        assertTrue(categories.get(1));
        assertFalse(categories.get(2));
    }

    @Test
    void testHistoryBasedRecommendation() {
        // 准备另一个浏览记录
        sampleRecord.setRoomId(2);
        BrowsingRecord anotherRecord = new BrowsingRecord();
        anotherRecord.setRoomId(1);
        anotherRecord.setWatchDuration(1800);
        sampleUserHistory.setBrowsingHistory(List.of(sampleRecord, anotherRecord));

        // 设置另一个房间信息
        RoomInfo anotherRoomInfo = new RoomInfo();
        anotherRoomInfo.setRoomID(2);
        anotherRoomInfo.setRoomName("Room2");
        anotherRoomInfo.setStatus(true);
        anotherRoomInfo.setStudy(true);
        anotherRoomInfo.setEntertain(false);
        anotherRoomInfo.setOther(false);

        // 准备房间热度索引数据
        RoomHotIndex roomHotIndex = new RoomHotIndex();
        roomHotIndex.setRoomId(2);
        roomHotIndex.setDuration(100);
        roomHotIndex.setViewCount(50);
        roomHotIndex.setLikeCount(5);
        roomHotIndex.setShareCount(2);
        roomHotIndex.setConsumptionCount(1);
        roomHotIndex.setMessageCount(3);
        roomHotIndex.setNewFollowerCount(10);
        roomHotIndex.setSumViewTime(5000);

        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("111")).thenReturn(sampleUserHistory);
        when(roomDao.findAll()).thenReturn(List.of(sampleRoomInfo, anotherRoomInfo));
        when(mongoTemplate.findOne(any(Query.class), eq(RoomHotIndex.class))).thenReturn(roomHotIndex);
        when(roomDao.findById(1)).thenReturn(sampleRoomInfo);
        when(roomDao.findById(2)).thenReturn(anotherRoomInfo);

        // 调用基于历史的推荐方法
        List<Integer> recommendedRooms = userBrowsHistoryService.HistoryBasedRecommendation("111");

        // 验证推荐结果
        assertFalse(recommendedRooms.isEmpty());
        assertTrue(recommendedRooms.contains(2)); // 确保推荐了符合条件的房间
    }

}
