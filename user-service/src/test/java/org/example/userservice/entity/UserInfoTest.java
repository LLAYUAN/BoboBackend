package org.example.userservice.entity;

import org.junit.jupiter.api.BeforeEach;

import java.sql.Date;
import java.time.LocalDate;

class UserInfoTest {

    private UserInfo userInfoUnderTest;

    @BeforeEach
    void setUp() {
        userInfoUnderTest = new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)));
    }
}
