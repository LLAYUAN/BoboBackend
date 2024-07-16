package org.example.recordvideoservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserInfoTest {

    private UserInfo userInfoUnderTest;

    @BeforeEach
    void setUp() {
        userInfoUnderTest = new UserInfo();
    }

    @Test
    void testGettersAndSetters() {
        // Setup
        Integer userID = 1;
        String password = "password";
        String email = "test@example.com";
        String avatarUrl = "http://test.com/avatar.jpg";
        String nickname = "nickname";
        String selfIntro = "self intro";
        Date birthday = Date.valueOf("1990-01-01");
        Boolean isAdmin = true;
        Set<RecordVideo> recordVideos = new HashSet<>();

        // Set values
        userInfoUnderTest.setUserID(userID);
        userInfoUnderTest.setPassword(password);
        userInfoUnderTest.setEmail(email);
        userInfoUnderTest.setAvatarUrl(avatarUrl);
        userInfoUnderTest.setNickname(nickname);
        userInfoUnderTest.setSelfIntro(selfIntro);
        userInfoUnderTest.setBirthday(birthday);
        userInfoUnderTest.setIsAdmin(isAdmin);
        userInfoUnderTest.setRecordVideo(recordVideos);

        // Verify the results
        assertThat(userInfoUnderTest.getUserID()).isEqualTo(userID);
        assertThat(userInfoUnderTest.getPassword()).isEqualTo(password);
        assertThat(userInfoUnderTest.getEmail()).isEqualTo(email);
        assertThat(userInfoUnderTest.getAvatarUrl()).isEqualTo(avatarUrl);
        assertThat(userInfoUnderTest.getNickname()).isEqualTo(nickname);
        assertThat(userInfoUnderTest.getSelfIntro()).isEqualTo(selfIntro);
        assertThat(userInfoUnderTest.getBirthday()).isEqualTo(birthday);
        assertThat(userInfoUnderTest.getIsAdmin()).isEqualTo(isAdmin);
        assertThat(userInfoUnderTest.getRecordVideo()).isEqualTo(recordVideos);
    }

    @Test
    void testNoArgsConstructor() {
        // Verify the results
        assertThat(userInfoUnderTest).isNotNull();
        assertThat(userInfoUnderTest.getUserID()).isNull();
        assertThat(userInfoUnderTest.getPassword()).isNull();
        assertThat(userInfoUnderTest.getEmail()).isNull();
        assertThat(userInfoUnderTest.getAvatarUrl()).isNull();
        assertThat(userInfoUnderTest.getNickname()).isNull();
        assertThat(userInfoUnderTest.getSelfIntro()).isNull();
        assertThat(userInfoUnderTest.getBirthday()).isNull();
        assertThat(userInfoUnderTest.getIsAdmin()).isNull();
        assertThat(userInfoUnderTest.getRecordVideo()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Setup
        Integer userID = 1;
        String password = "password";
        String email = "test@example.com";
        String avatarUrl = "http://test.com/avatar.jpg";
        String nickname = "nickname";
        String selfIntro = "self intro";
        Date birthday = Date.valueOf("1990-01-01");
        Boolean isAdmin = true;
        Set<RecordVideo> recordVideos = new HashSet<>();

        // Run the test
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(userID);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setAvatarUrl(avatarUrl);
        userInfo.setNickname(nickname);
        userInfo.setSelfIntro(selfIntro);
        userInfo.setBirthday(birthday);
        userInfo.setIsAdmin(isAdmin);
        userInfo.setRecordVideo(recordVideos);

        // Verify the results
        assertThat(userInfo.getUserID()).isEqualTo(userID);
        assertThat(userInfo.getPassword()).isEqualTo(password);
        assertThat(userInfo.getEmail()).isEqualTo(email);
        assertThat(userInfo.getAvatarUrl()).isEqualTo(avatarUrl);
        assertThat(userInfo.getNickname()).isEqualTo(nickname);
        assertThat(userInfo.getSelfIntro()).isEqualTo(selfIntro);
        assertThat(userInfo.getBirthday()).isEqualTo(birthday);
        assertThat(userInfo.getIsAdmin()).isEqualTo(isAdmin);
        assertThat(userInfo.getRecordVideo()).isEqualTo(recordVideos);
    }
}
