package org.example.userservice.entity;

import org.junit.jupiter.api.BeforeEach;

class RoomHotIndexTest {

    private RoomHotIndex roomHotIndexUnderTest;

    @BeforeEach
    void setUp() {
        roomHotIndexUnderTest = new RoomHotIndex(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }
}
