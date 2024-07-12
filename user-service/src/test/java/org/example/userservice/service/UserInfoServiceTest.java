package org.example.userservice.service;

import org.example.userservice.Feign.Feign;
import org.example.userservice.dao.CompleteUserDao;
import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.model.PasswordRequest;
import org.example.userservice.repository.RoomInfoRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {

    @Mock
    private CompleteUserDao mockCompleteUserDao;
    @Mock
    private RoomInfoRepo mockRoomInfoRepo;
    @Mock
    private Feign mockFeign;

    @InjectMocks
    private UserInfoService userInfoServiceUnderTest;

    @Test
    void testLogin() {
        // Setup
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));

        // Configure CompleteUserDao.findUserInfoByEmail(...).
        final UserInfo userInfo1 = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(userInfo1);

        when(mockFeign.matchPassword(new PasswordRequest("oldPassword", "encodedPassword"))).thenReturn(false);

        // Run the test
        final String result = userInfoServiceUnderTest.login("email", "oldPassword", userInfo);

        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @Test
    void testLogin_FeignMatchPasswordReturnsTrue() {
        // Setup
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));

        // Configure CompleteUserDao.findUserInfoByEmail(...).
        final UserInfo userInfo1 = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(userInfo1);

        when(mockFeign.matchPassword(new PasswordRequest("oldPassword", "encodedPassword"))).thenReturn(true);
        when(mockFeign.generateToken(0)).thenReturn("result");

        // Run the test
        final String result = userInfoServiceUnderTest.login("email", "oldPassword", userInfo);

        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @Test
    void testFindUserInfoByEmail() {
        // Setup
        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));

        // Configure CompleteUserDao.findUserInfoByEmail(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(userInfo);

        // Run the test
        final UserInfo result = userInfoServiceUnderTest.findUserInfoByEmail("email");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindUserInfoByUserID() {
        // Setup
        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));

        // Configure CompleteUserDao.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);

        // Run the test
        final UserInfo result = userInfoServiceUnderTest.findUserInfoByUserID(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetRoomIDByUserID() {
        // Setup
        // Configure CompleteUserDao.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);

        // Run the test
        final Integer result = userInfoServiceUnderTest.getRoomIDByUserID(0);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetRoomInfoByUserID() {
        // Setup
        final RoomInfo expectedResult = new RoomInfo("roomName", "description", "coverUrl");

        // Configure CompleteUserDao.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);

        // Run the test
        final RoomInfo result = userInfoServiceUnderTest.getRoomInfoByUserID(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRoomInfoRepo).save(new RoomInfo("roomName", "description", "coverUrl"));
    }

    @Test
    void testRegister() {
        // Setup
        // Configure CompleteUserDao.findUserInfoByEmail(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(userInfo);

        // Run the test
        final UserInfo result = userInfoServiceUnderTest.register("email", "password");

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void testRegister_CompleteUserDaoFindUserInfoByEmailReturnsNull() {
        // Setup
        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(null);
        when(mockFeign.encode("password")).thenReturn("encodedPassword");

        // Configure CompleteUserDao.save(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.save(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)))))
                .thenReturn(userInfo);

        // Run the test
        final UserInfo result = userInfoServiceUnderTest.register("email", "password");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSave() {
        // Setup
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));

        // Configure CompleteUserDao.save(...).
        final UserInfo userInfo1 = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.save(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)))))
                .thenReturn(userInfo1);

        // Run the test
        final UserInfo result = userInfoServiceUnderTest.save(userInfo);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testModifyPassword() {
        // Setup
        // Configure CompleteUserDao.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);

        when(mockFeign.matchPassword(new PasswordRequest("oldPassword", "encodedPassword"))).thenReturn(false);

        // Run the test
        final Boolean result = userInfoServiceUnderTest.modifyPassword(0, "oldPassword", "newPassword");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testModifyPassword_FeignMatchPasswordReturnsTrue() {
        // Setup
        // Configure CompleteUserDao.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);

        when(mockFeign.matchPassword(new PasswordRequest("oldPassword", "encodedPassword"))).thenReturn(true);
        when(mockFeign.encode("newPassword")).thenReturn("encodedPassword");

        // Run the test
        final Boolean result = userInfoServiceUnderTest.modifyPassword(0, "oldPassword", "newPassword");

        // Verify the results
        assertThat(result).isTrue();
        verify(mockCompleteUserDao).save(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1))));
    }
}
