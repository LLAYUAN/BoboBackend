package org.example.userservice.dao;

import org.example.userservice.entity.RoomInfo;
import org.example.userservice.repository.RoomInfoRepo;
import org.example.userservice.repository.UserInfoRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoomDaoTest {

    @Mock
    private RoomInfoRepo mockRoomInfoRepo;
    @Mock
    private UserInfoRepo mockUserInfoRepo;

    @InjectMocks
    private RoomDao roomDaoUnderTest;

    @Test
    void testStartLive() {
        roomDaoUnderTest.startLive(0);
    }

    @Test
    void testSaveRoomInfo() {
        // Setup
        final RoomInfo roomInfo = new RoomInfo("roomName", "description", "coverUrl");

        // Run the test
        roomDaoUnderTest.saveRoomInfo(roomInfo);

        // Verify the results
        verify(mockRoomInfoRepo).save(new RoomInfo("roomName", "description", "coverUrl"));
    }
}
