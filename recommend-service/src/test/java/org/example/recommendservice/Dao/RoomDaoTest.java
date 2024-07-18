package org.example.recommendservice.Dao;

import org.example.recommendservice.entity.RoomHotIndex;
import org.example.recommendservice.entity.RoomInfo;
import org.example.recommendservice.MySQLRepository.RoomInfoRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ComponentScan(basePackages = "org.example.recommendservice.Dao")
@ExtendWith(SpringExtension.class)
public class RoomDaoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @MockBean
    private RoomInfoRepo roomInfoRepo;

    @Autowired
    private RoomDao roomDao;

    @Test
    public void testSaveRoomHotIndex() {
        RoomHotIndex roomHotIndex = new RoomHotIndex(1, 10, 5, 2, 3, 4, 6, 6, 100);

        roomDao.saveRoomHotIndex(roomHotIndex);

        RoomHotIndex savedRoomHotIndex = mongoTemplate.findById(roomHotIndex.getRoomId(), RoomHotIndex.class);
        assertThat(savedRoomHotIndex).isNotNull();
        assertThat(savedRoomHotIndex.getRoomId()).isEqualTo(roomHotIndex.getRoomId());
    }

    @Test
    public void testGetRoomHotIndex() {
        RoomHotIndex roomHotIndex = new RoomHotIndex(1, 10, 5, 2, 3, 4, 6, 6, 100);
        mongoTemplate.save(roomHotIndex);

        RoomHotIndex retrievedRoomHotIndex = roomDao.getRoomHotIndex(1);

        assertThat(retrievedRoomHotIndex).isNotNull();
        assertThat(retrievedRoomHotIndex.getRoomId()).isEqualTo(roomHotIndex.getRoomId());
    }

    @Test
    public void testGetAllRoomInfo() {
        RoomInfo room1 = new RoomInfo("1", "Room 1", "Description 1");
        RoomInfo room2 = new RoomInfo("2", "Room 2", "Description 2");
        List<RoomInfo> roomList = Arrays.asList(room1, room2);

        Mockito.when(roomInfoRepo.findAll()).thenReturn(roomList);

        List<RoomInfo> retrievedRoomList = roomDao.getAllRoomInfo();

        assertThat(retrievedRoomList).isNotNull();
        assertThat(retrievedRoomList.size()).isEqualTo(2);
        assertThat(retrievedRoomList.get(0).getRoomID()).isEqualTo(room1.getRoomID());
        assertThat(retrievedRoomList.get(1).getRoomID()).isEqualTo(room2.getRoomID());
    }

    @Test
    public void testGetRoomInfo() {
        RoomInfo room1 = new RoomInfo("1", "Room 1", "Description 1");

        Mockito.when(roomInfoRepo.findById(1)).thenReturn(Optional.of(room1));

        RoomInfo retrievedRoomInfo = roomDao.getRoomInfo(1);

        assertThat(retrievedRoomInfo).isNotNull();
        assertThat(retrievedRoomInfo.getRoomID()).isEqualTo(room1.getRoomID());
    }

    @Test
    public void testSaveRoomHotIndexList() {
        RoomHotIndex roomHotIndex1 = new RoomHotIndex(1, 10, 5, 2, 3, 4, 6, 6, 100);
        RoomHotIndex roomHotIndex2 = new RoomHotIndex(2, 15, 10, 3, 4, 5, 7, 8, 150);
        List<RoomHotIndex> roomHotIndexList = Arrays.asList(roomHotIndex1, roomHotIndex2);

        roomDao.saveRoomHotIndexList(roomHotIndexList);

        RoomHotIndex savedRoomHotIndex1 = mongoTemplate.findById(roomHotIndex1.getRoomId(), RoomHotIndex.class);
        RoomHotIndex savedRoomHotIndex2 = mongoTemplate.findById(roomHotIndex2.getRoomId(), RoomHotIndex.class);

        assertThat(savedRoomHotIndex1).isNotNull();
        assertThat(savedRoomHotIndex1.getRoomId()).isEqualTo(roomHotIndex1.getRoomId());
        assertThat(savedRoomHotIndex2).isNotNull();
        assertThat(savedRoomHotIndex2.getRoomId()).isEqualTo(roomHotIndex2.getRoomId());
    }

    @Test
    public void testFindAll() {
        RoomInfo room1 = new RoomInfo("1", "Room 1", "Description 1");
        RoomInfo room2 = new RoomInfo("2", "Room 2", "Description 2");
        List<RoomInfo> roomList = Arrays.asList(room1, room2);

        Mockito.when(roomInfoRepo.findAll()).thenReturn(roomList);

        List<RoomInfo> retrievedRoomList = roomDao.findAll();

        assertThat(retrievedRoomList).isNotNull();
        assertThat(retrievedRoomList.size()).isEqualTo(2);
        assertThat(retrievedRoomList.get(0).getRoomID()).isEqualTo(room1.getRoomID());
        assertThat(retrievedRoomList.get(1).getRoomID()).isEqualTo(room2.getRoomID());
    }

    @Test
    public void testFindById() {
        RoomInfo room1 = new RoomInfo("1", "Room 1", "Description 1");

        Mockito.when(roomInfoRepo.findById(1)).thenReturn(Optional.of(room1));

        RoomInfo retrievedRoomInfo = roomDao.findById(1);

        assertThat(retrievedRoomInfo).isNotNull();
        assertThat(retrievedRoomInfo.getRoomID()).isEqualTo(room1.getRoomID());
    }
}
