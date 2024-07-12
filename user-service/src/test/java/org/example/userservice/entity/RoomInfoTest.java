package org.example.userservice.entity;

import org.junit.jupiter.api.BeforeEach;

class RoomInfoTest {

    private RoomInfo roomInfoUnderTest;

    @BeforeEach
    void setUp() {
        roomInfoUnderTest = new RoomInfo("roomName", "description", "coverUrl");
    }
}
