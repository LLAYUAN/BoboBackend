package org.example.userservice.entity;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

class FollowerInfoTest {

    private FollowerInfo followerInfoUnderTest;

    @BeforeEach
    void setUp() {
        followerInfoUnderTest = new FollowerInfo("id", 0, 0, LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    }
}
