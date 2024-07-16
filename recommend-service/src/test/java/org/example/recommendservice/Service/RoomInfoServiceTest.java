package org.example.recommendservice.Service;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.RoomHotIndex;
import org.example.recommendservice.entity.RoomInfo;
import org.example.recommendservice.entity.UserInfo;
import org.example.recommendservice.utils.HotIndexCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoomInfoServiceTest {

    @InjectMocks
    private RoomInfoService roomInfoService;

    @Mock
    private RoomDao roomDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRank() {
        // 准备测试数据
        List<RoomInfo> roomInfos = new ArrayList<>();
        UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)));
        userInfo.setUserID(1);
        roomInfos.add(new RoomInfo(1, "room1", "desc1", true, "cover1", true, false, false, userInfo));
        roomInfos.add(new RoomInfo(2, "room2", "desc2", true, "cover2", false, true, false, userInfo));
        roomInfos.add(new RoomInfo(3, "room3", "desc3", false, "cover3", false, false, true, userInfo));

        when(roomDao.getAllRoomInfo()).thenReturn(roomInfos);

        RoomHotIndex roomHotIndex1 = new RoomHotIndex(1, 0, 100, 50, 30, 20, 10, 5, 200);
        RoomHotIndex roomHotIndex2 = new RoomHotIndex(2, 0, 90, 40, 20, 10, 5, 2, 150);

        when(roomDao.getRoomHotIndex(1)).thenReturn(roomHotIndex1);
        when(roomDao.getRoomHotIndex(2)).thenReturn(roomHotIndex2);
        when(roomDao.getRoomHotIndex(3)).thenReturn(null);

        // 调用测试方法
        List<RoomCardInfo> result = roomInfoService.getRank(-1);

        // 验证结果
        assertEquals(2, result.size());
        assertEquals(HotIndexCalculator.calculateHotIndex(roomHotIndex1), result.get(0).getHotIndex());
        assertEquals(HotIndexCalculator.calculateHotIndex(roomHotIndex2), result.get(1).getHotIndex());
    }

    @Test
    void getRoomInfo() {
        // 准备测试数据
        UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)));
        userInfo.setUserID(1);
        RoomInfo roomInfo = new RoomInfo(1, "room1", "desc1", true, "cover1", true, false, false, userInfo);
        RoomHotIndex roomHotIndex = new RoomHotIndex(1, 0, 100, 50, 30, 20, 10, 5, 200);

        when(roomDao.getRoomInfo(1)).thenReturn(roomInfo);
        when(roomDao.getRoomHotIndex(1)).thenReturn(roomHotIndex);

        // 调用测试方法
        RoomCardInfo result = roomInfoService.getRoomInfo(1);

        // 验证结果
        assertEquals(roomInfo.getRoomName(), result.getRoomName());
        assertEquals(HotIndexCalculator.calculateHotIndex(roomHotIndex), result.getHotIndex());
    }
    @Test
    void saveRoomHotIndexList() {
        // 准备测试数据
        List<RoomHotIndex> roomHotIndexList = new ArrayList<>();
        RoomHotIndex roomHotIndex1 = new RoomHotIndex(1, 0, 100, 50, 30, 20, 10, 5, 200);
        RoomHotIndex roomHotIndex2 = new RoomHotIndex(2, 0, 90, 40, 20, 10, 5, 2, 150);
        roomHotIndexList.add(roomHotIndex1);
        roomHotIndexList.add(roomHotIndex2);

        // 调用方法
        roomInfoService.saveRoomHotIndexList(roomHotIndexList);

        // 验证是否正确调用了 roomDao.saveRoomHotIndexList 方法，并且参数正确
        verify(roomDao, times(1)).saveRoomHotIndexList(eq(roomHotIndexList));
    }

}
