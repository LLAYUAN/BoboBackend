package org.example.userservice.dto;

import org.junit.jupiter.api.BeforeEach;

class BasicRoomDTOTest {

    private BasicRoomDTO basicRoomDTOUnderTest;

    @BeforeEach
    void setUp() {
        basicRoomDTOUnderTest = new BasicRoomDTO(0, "roomName", new Integer[]{0}, "coverUrl");
    }
}
