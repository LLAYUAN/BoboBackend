package org.example.userservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    private UserInfo userInfoUnderTest;

    @BeforeEach
    void setUp() {
        userInfoUnderTest = new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)));
    }

    @Test
    void testConstructorWithParameters() {
        // Test constructor with parameters
        assertEquals("email", userInfoUnderTest.getEmail());
        assertEquals("nickname", userInfoUnderTest.getNickname());
        assertEquals("selfIntro", userInfoUnderTest.getSelfIntro());
        assertEquals(Date.valueOf(LocalDate.of(2020, 1, 1)), userInfoUnderTest.getBirthday());
        assertEquals(false, userInfoUnderTest.getIsAdmin());
        assertNull(userInfoUnderTest.getUserID());
        assertNull(userInfoUnderTest.getPassword());
        assertNull(userInfoUnderTest.getAvatarUrl());
    }

    @Test
    void testConstructorWithEmailPassword() {
        // Test constructor with email and password
        UserInfo emailPasswordUserInfo = new UserInfo("email", "password");
        assertEquals("email", emailPasswordUserInfo.getEmail());
        assertEquals("password", emailPasswordUserInfo.getPassword());
        assertEquals(false, emailPasswordUserInfo.getIsAdmin());
        assertNull(emailPasswordUserInfo.getUserID());
        assertNull(emailPasswordUserInfo.getNickname());
        assertNull(emailPasswordUserInfo.getSelfIntro());
        assertNull(emailPasswordUserInfo.getBirthday());
        assertNull(emailPasswordUserInfo.getAvatarUrl());
    }

    @Test
    void testNoArgsConstructor() {
        // Test no-args constructor
        UserInfo emptyUserInfo = new UserInfo();
        assertNull(emptyUserInfo.getUserID());
        assertNull(emptyUserInfo.getEmail());
        assertNull(emptyUserInfo.getPassword());
        assertNull(emptyUserInfo.getNickname());
        assertNull(emptyUserInfo.getSelfIntro());
        assertNull(emptyUserInfo.getBirthday());
        assertNull(emptyUserInfo.getIsAdmin());
        assertNull(emptyUserInfo.getAvatarUrl());
    }

    @Test
    void testToString() {
        // Test toString method
        String expectedToString = "UserInfo(userID=null, password=null, email=email, nickname=nickname, selfIntro=selfIntro, birthday=2020-01-01, isAdmin=false, avatarUrl=null, roomInfo=null)";
        assertEquals(expectedToString, userInfoUnderTest.toString());
    }

    @Test
    void testHashCode() {
        // Test hashCode method
        UserInfo sameUserInfo = new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)));
        assertEquals(sameUserInfo.hashCode(), userInfoUnderTest.hashCode());

        UserInfo differentUserInfo = new UserInfo("differentEmail", "differentNickname", "differentSelfIntro", Date.valueOf(LocalDate.of(2021, 1, 1)));
        assertNotEquals(differentUserInfo.hashCode(), userInfoUnderTest.hashCode());
    }

    @Test
    void testSetterAndGetters() {
        // Test setter and getters
        userInfoUnderTest.setEmail("newEmail");
        userInfoUnderTest.setNickname("newNickname");
        userInfoUnderTest.setSelfIntro("newSelfIntro");
        userInfoUnderTest.setBirthday(Date.valueOf(LocalDate.of(2022, 1, 1)));
        userInfoUnderTest.setIsAdmin(true);
        userInfoUnderTest.setAvatarUrl("newAvatarUrl");

        assertEquals("newEmail", userInfoUnderTest.getEmail());
        assertEquals("newNickname", userInfoUnderTest.getNickname());
        assertEquals("newSelfIntro", userInfoUnderTest.getSelfIntro());
        assertEquals(Date.valueOf(LocalDate.of(2022, 1, 1)), userInfoUnderTest.getBirthday());
        assertEquals(true, userInfoUnderTest.getIsAdmin());
        assertEquals("newAvatarUrl", userInfoUnderTest.getAvatarUrl());
    }
}
