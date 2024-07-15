package org.example.livevideoservice.entity;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

class UserActivityTest {

    private UserActivity userActivityUnderTest;

    @BeforeEach
    void setUp() {
        userActivityUnderTest = new UserActivity("id", "userId", "roomId", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    }
}
