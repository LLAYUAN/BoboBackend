package org.example.recommendservice.Service;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.BrowsHistoryDao;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    List<RoomInfo> roomInfoList;
    private RoomInfo sampleRoomInfo1;
    private RoomInfo sampleRoomInfo2;

    private BrowsingRecord sampleRecord1;
    private BrowsingRecord sampleRecord2;
    private UserBrowsHistory userHistory1;
    private UserBrowsHistory userHistory2;

    List<UserBrowsHistory> userBrowsHistoryList;

    private RoomHotIndex roomHotIndex;
    @BeforeEach
    void setUp() {
        // 设置示例房间信息
        sampleRoomInfo1 = new RoomInfo();
        sampleRoomInfo1.setRoomID(111111);
        sampleRoomInfo1.setRoomName("Room1");
        sampleRoomInfo1.setDescription("Description");
        sampleRoomInfo1.setStatus(true);
        sampleRoomInfo1.setCoverUrl("coverUrl");
        sampleRoomInfo1.setStudy(false);
        sampleRoomInfo1.setEntertain(true);
        sampleRoomInfo1.setOther(false);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(111);
        sampleRoomInfo1.setUserInfo(userInfo);

        sampleRoomInfo2 = new RoomInfo();
        sampleRoomInfo2.setRoomID(222222);
        sampleRoomInfo2.setRoomName("Room1");
        sampleRoomInfo2.setDescription("Description");
        sampleRoomInfo2.setStatus(true);
        sampleRoomInfo2.setCoverUrl("coverUrl");
        sampleRoomInfo2.setStudy(false);
        sampleRoomInfo2.setEntertain(true);
        sampleRoomInfo2.setOther(false);
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setUserID(2);
        sampleRoomInfo2.setUserInfo(userInfo);

        roomInfoList = new ArrayList<>();
        roomInfoList.add(sampleRoomInfo1);
        roomInfoList.add(sampleRoomInfo2);

        // 设置示例浏览记录
        sampleRecord1 = new BrowsingRecord();
        sampleRecord1.setRoomId(111111);
        sampleRecord1.setLikeCount(5);
        sampleRecord1.setShareCount(2);
        sampleRecord1.setConsumptionCount(1);
        sampleRecord1.setMessageCount(3);
        sampleRecord1.setFollowStatus(1);
        sampleRecord1.setStartTime(LocalDateTime.now());
        sampleRecord1.setWatchDuration(3600);

        sampleRecord2 = new BrowsingRecord();
        sampleRecord2.setRoomId(222222);
        sampleRecord2.setLikeCount(5);
        sampleRecord2.setShareCount(2);
        sampleRecord2.setConsumptionCount(1);
        sampleRecord2.setMessageCount(3);
        sampleRecord2.setFollowStatus(1);
        sampleRecord2.setStartTime(LocalDateTime.now());
        sampleRecord2.setWatchDuration(3600);

        // 设置示例用户浏览历史
        userHistory1 = new UserBrowsHistory();
        userHistory1.setUserId("1");
        ArrayList<BrowsingRecord> browsingHistory = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            browsingHistory.add(sampleRecord1);
        }
        userHistory1.setBrowsingHistory(browsingHistory);

        userHistory2 = new UserBrowsHistory();
        userHistory2.setUserId("2");
        userHistory2.setBrowsingHistory(new ArrayList<>(List.of(sampleRecord2)));

        userBrowsHistoryList = new ArrayList<>();
        userBrowsHistoryList.add(userHistory1);
        userBrowsHistoryList.add(userHistory2);

        // 准备房间热度索引数据
        roomHotIndex = new RoomHotIndex();
        roomHotIndex.setRoomId(111111);
        roomHotIndex.setStartTime(LocalDateTime("2021-08-31T20:00:04.000Z"));
        roomHotIndex.setViewCount(50);
        roomHotIndex.setLikeCount(5);
        roomHotIndex.setShareCount(2);
        roomHotIndex.setConsumptionCount(1);
        roomHotIndex.setMessageCount(3);
        roomHotIndex.setNewFollowerCount(10);
        roomHotIndex.setSumViewTime(5000);
    }
    @Test
    void testAddBrowsingRecordWhenNull() {
        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("1")).thenReturn(null);

        // 调用服务方法
        userBrowsHistoryService.addBrowsingRecord("1", sampleRecord1);

        // 验证保存方法是否被调用一次
        verify(browsHistoryDao, times(1)).saveUserBrowsHistory(any(UserBrowsHistory.class));

        // 验证添加后的浏览记录
        UserBrowsHistory updatedUserHistory = browsHistoryDao.getUserBrowsHistory("111");
        assertNull(updatedUserHistory);
        updatedUserHistory = userHistory1;
        List<BrowsingRecord> updatedRecords = updatedUserHistory.getBrowsingHistory();
        assertFalse(updatedRecords.isEmpty());
        assertEquals(30, updatedRecords.size()); // 确保添加成功
        assertEquals(sampleRecord1, updatedRecords.get(0)); // 确保按时间排序
    }
    @Test
    void testAddBrowsingRecord() {
        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("1")).thenReturn(userHistory1);

        // 调用服务方法
        userBrowsHistoryService.addBrowsingRecord("1", sampleRecord1);

        // 验证保存方法是否被调用一次
        verify(browsHistoryDao, times(1)).saveUserBrowsHistory(any(UserBrowsHistory.class));

        // 验证添加后的浏览记录
        UserBrowsHistory updatedUserHistory = browsHistoryDao.getUserBrowsHistory("1");
        assertNotNull(updatedUserHistory);
        List<BrowsingRecord> updatedRecords = updatedUserHistory.getBrowsingHistory();
        assertFalse(updatedRecords.isEmpty());
        assertEquals(30, updatedRecords.size()); // 确保添加成功
        assertEquals(sampleRecord1, updatedRecords.get(0)); // 确保按时间排序
    }
    @Test
    void testSetBrowsingRecordWhenNull() {
        // 准备新的浏览记录
        List<BrowsingRecord> newHistory = new ArrayList<>(List.of(sampleRecord1));

        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("1")).thenReturn(null);

        // 调用服务方法
        userBrowsHistoryService.setBrowsingRecord("1", newHistory);

        // 验证保存方法是否被调用一次
        verify(browsHistoryDao, times(1)).saveUserBrowsHistory(any(UserBrowsHistory.class));

        // 验证设置后的浏览记录
        UserBrowsHistory updatedUserHistory = browsHistoryDao.getUserBrowsHistory("1");
        assertNull(updatedUserHistory);
        updatedUserHistory = userHistory1;
        List<BrowsingRecord> updatedRecords = updatedUserHistory.getBrowsingHistory();
        assertFalse(updatedRecords.isEmpty());
        assertEquals(30, updatedRecords.size()); // 确保设置成功
        assertEquals(sampleRecord1, updatedRecords.get(0)); // 确保设置正确
    }
    @Test
    void testSetBrowsingRecord() {
        // 准备新的浏览记录
        List<BrowsingRecord> newHistory = new ArrayList<>(List.of(sampleRecord1));

        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("1")).thenReturn(userHistory1);

        // 调用服务方法
        userBrowsHistoryService.setBrowsingRecord("1", newHistory);

        // 验证保存方法是否被调用一次
        verify(browsHistoryDao, times(1)).saveUserBrowsHistory(any(UserBrowsHistory.class));

        // 验证设置后的浏览记录
        UserBrowsHistory updatedUserHistory = browsHistoryDao.getUserBrowsHistory("1");
        assertNotNull(updatedUserHistory);
        List<BrowsingRecord> updatedRecords = updatedUserHistory.getBrowsingHistory();
        assertFalse(updatedRecords.isEmpty());
        assertEquals(1, updatedRecords.size()); // 确保设置成功
        assertEquals(sampleRecord1, updatedRecords.get(0)); // 确保设置正确
    }
    @Test
    void testRecommendRooms() {

        // 模拟房间Dao返回数据
        when(roomDao.findAll()).thenReturn(roomInfoList);
        when(browsHistoryDao.getUserBrowsHistory("1")).thenReturn(userHistory1);
        when(browsHistoryDao.getUserBrowsHistory("2")).thenReturn(userHistory2);
        when(browsHistoryDao.getAllUserBrowsHistory()).thenReturn(userBrowsHistoryList);
        when(roomDao.findById(111111)).thenReturn(sampleRoomInfo1);
        when(roomDao.findById(222222)).thenReturn(sampleRoomInfo2);

        when(mongoTemplate.findOne(any(Query.class), eq(RoomHotIndex.class))).thenReturn(roomHotIndex);


        // 调用推荐方法
        List<RoomCardInfo> recommendedRooms = userBrowsHistoryService.recommendRooms("1");

        // 验证推荐结果
        assertFalse(recommendedRooms.isEmpty());
        assertEquals(2, recommendedRooms.size());
        assertEquals(222222, recommendedRooms.get(0).getId()); // 确保推荐的是期望的房间
    }
    @Test
    void testUserBasedCF() {
        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("1")).thenReturn(null);
        // 调用基于历史的推荐方法
        List<Integer> recommendedRooms = userBrowsHistoryService.UserBasedCF("1");
    }
    @Test
    void testHistoryBasedRecommendation() {
        // 模拟Dao层返回用户浏览历史
        when(browsHistoryDao.getUserBrowsHistory("1")).thenReturn(null);
        // 调用基于历史的推荐方法
        List<Integer> recommendedRooms = userBrowsHistoryService.HistoryBasedRecommendation("1");
    }

}
