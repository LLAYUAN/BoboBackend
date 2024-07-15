package org.example.livevideoservice.entity;

import org.junit.jupiter.api.BeforeEach;

class RoomInfoTest {

    private RoomInfo roomInfoUnderTest;

    @BeforeEach
    void setUp() {
        roomInfoUnderTest = new RoomInfo(0, "roomName", "description", false, "coverUrl", false, false, false);
    }
}
