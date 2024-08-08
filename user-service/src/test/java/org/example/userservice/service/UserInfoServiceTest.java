//package org.example.userservice.service;
//
//import org.example.userservice.Feign.Feign;
//import org.example.userservice.dao.CompleteUserDao;
//import org.example.userservice.entity.RoomInfo;
//import org.example.userservice.entity.UserInfo;
//import org.example.userservice.model.PasswordRequest;
//import org.example.userservice.repository.RoomInfoRepo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.Logger;
//
//import java.sql.Date;
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserInfoServiceTest {
//
//    @Mock
//    private CompleteUserDao mockCompleteUserDao;
//    @Mock
//    private RoomInfoRepo mockRoomInfoRepo;
//    @Mock
//    private Feign mockFeign;
//
//    @InjectMocks
//    private UserInfoService userInfoServiceUnderTest;
//
//    @Test
//    void testLogin() {
//        // Setup
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//
//        // Configure CompleteUserDao.findUserInfoByEmail(...).
//        final UserInfo userInfo1 = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(userInfo1);
//
//        // Stubbing matchPassword method with any arguments
//        when(mockFeign.matchPassword(any(PasswordRequest.class))).thenReturn(false);
//
//        // Run the test
//        final String result = userInfoServiceUnderTest.login("email", "oldPassword", userInfo);
//
//        // Verify the results
//        assertThat(result).isEqualTo(null);
//    }
//
//
//
//    @Test
//    void testLogin_FeignMatchPasswordReturnsTrue() {
//        // Setup
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        userInfo.setUserID(0);
//
//        // Configure CompleteUserDao.findUserInfoByEmail(...).
//        final UserInfo userInfo1 = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        userInfo1.setUserID(0);
//        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(userInfo1);
//
//        // Stubbing matchPassword method with any arguments
//        when(mockFeign.matchPassword(any(PasswordRequest.class))).thenReturn(true);
//        when(mockFeign.generateToken(0)).thenReturn("result");
//
//        // Run the test
//        final String result = userInfoServiceUnderTest.login("email", "oldPassword", userInfo);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }
//
//
//    @Test
//    void testFindUserInfoByEmail() {
//        // Setup
//        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//
//        // Configure CompleteUserDao.findUserInfoByEmail(...).
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(userInfo);
//
//        // Run the test
//        final UserInfo result = userInfoServiceUnderTest.findUserInfoByEmail("email");
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//    }
//
//    @Test
//    void testFindUserInfoByUserID() {
//        // Setup
//        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//
//        // Configure CompleteUserDao.findUserInfoByUserID(...).
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);
//
//        // Run the test
//        final UserInfo result = userInfoServiceUnderTest.findUserInfoByUserID(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//    }
//
//    @Test
//    void testLogin_UserInfoNull() {
//        // Setup
//        final String email = "email@example.com";
//        final String password = "password";
//        final UserInfo userInfo = null;
//
//        when(mockCompleteUserDao.findUserInfoByEmail(email)).thenReturn(userInfo);
//
//        // Run the test
//        final String result = userInfoServiceUnderTest.login(email, password, null);
//
//        // Verify the results
//        assertEquals(null, result); // Assuming your login method returns null when userInfo is null
//        verify(mockCompleteUserDao, times(1)).findUserInfoByEmail(email);
//        verify(mockFeign, never()).matchPassword(any()); // Ensure matchPassword is never called
//        verify(mockFeign, never()).generateToken(anyInt()); // Ensure generateToken is never called
//    }
//
//    @Test
//    void testGetRoomInfoByUserID_roomInfoNotNull() {
//        // Setup
//        final RoomInfo expectedResult = new RoomInfo("直播间", "视频简介", "");
//        expectedResult.setUserInfo(new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1))));
//
//        // Configure CompleteUserDao.findUserInfoByUserID(...).
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        userInfo.setRoomInfo(expectedResult);
//        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);
//
//        // Run the test
//        final RoomInfo result = userInfoServiceUnderTest.getRoomInfoByUserID(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//    }
//
//    @Test
//    void testGetRoomIDByUserID() {
//        // Setup
//        final RoomInfo roomInfo = new RoomInfo("直播间", "视频简介", "");
//        roomInfo.setRoomID(0);
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        userInfo.setRoomInfo(roomInfo); // 关联有效的 RoomInfo 对象
//        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);
//
//        // Run the test
//        final Integer result = userInfoServiceUnderTest.getRoomIDByUserID(0);
//        System.out.println(result);
//
//        // Verify the results
//        assertThat(result).isEqualTo(0);
//    }
//
//
//    @Test
//    void testGetRoomInfoByUserID() {
//        // Setup
//        final RoomInfo expectedResult = new RoomInfo("直播间", "视频简介", "");
//        expectedResult.setUserInfo(new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1))));
//
//        // Configure CompleteUserDao.findUserInfoByUserID(...).
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);
//
//        // Run the test
//        final RoomInfo result = userInfoServiceUnderTest.getRoomInfoByUserID(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//        verify(mockRoomInfoRepo).save(expectedResult);
//    }
//
//
//
//    @Test
//    void testRegister() {
//        // Setup
//        // Configure CompleteUserDao.findUserInfoByEmail(...).
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(userInfo);
//
//        // Run the test
//        final UserInfo result = userInfoServiceUnderTest.register("email", "password");
//
//        // Verify the results
//        assertThat(result).isNull();
//    }
//
//    @Test
//    void testRegister_CompleteUserDaoFindUserInfoByEmailReturnsNull() {
//        // Setup
//        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.findUserInfoByEmail("email")).thenReturn(null);
//        when(mockFeign.encode("password")).thenReturn("encodedPassword");
//        when(mockCompleteUserDao.save(new UserInfo("email", "encodedPassword"))).thenReturn(expectedResult);
//
//        // Run the test
//        final UserInfo result = userInfoServiceUnderTest.register("email", "password");
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//        verify(mockCompleteUserDao, times(1)).save(any(UserInfo.class));
//    }
//
//    @Test
//    void testSave() {
//        // Setup
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//
//        // Configure CompleteUserDao.save(...).
//        final UserInfo userInfo1 = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.save(
//                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)))))
//                .thenReturn(userInfo1);
//
//        // Run the test
//        final UserInfo result = userInfoServiceUnderTest.save(userInfo);
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//    }
//
//    @Test
//    void testModifyPassword() {
//        // Setup
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);
//
//        // Stubbing matchPassword method
//        when(mockFeign.matchPassword(any(PasswordRequest.class))).thenReturn(false);
//
//        // Run the test
//        final boolean result = userInfoServiceUnderTest.modifyPassword(0, "oldPassword", "newPassword");
//
//        // Verify the results
//        assertFalse(result);
//    }
//
//
//    @Test
//    void testModifyPassword_FeignMatchPasswordReturnsTrue() {
//        // Setup
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);
//
//        // Stubbing matchPassword method
//        when(mockFeign.matchPassword(any(PasswordRequest.class))).thenReturn(false);
//        when(mockFeign.matchPassword(new PasswordRequest("oldPassword", null))).thenReturn(true); // Ensure only one stubbing
//
//        when(mockFeign.encode("newPassword")).thenReturn("encodedPassword");
//
//        // Run the test
//        final Boolean result = userInfoServiceUnderTest.modifyPassword(0, "oldPassword", "newPassword");
//
//        // Verify the results
//        assertThat(result).isTrue();
//        final UserInfo expectedUserInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        userInfo.setPassword("encodedPassword");
//        verify(mockCompleteUserDao).save(userInfo);
//    }
//}
