//package org.example.userservice.dao;
//
//import org.example.userservice.entity.UserInfo;
//import org.example.userservice.repository.UserInfoRepo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.sql.Date;
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class CompleteUserDaoTest {
//
//    @Mock
//    private UserInfoRepo mockUserInfoRepo;
//
//    @InjectMocks
//    private CompleteUserDao completeUserDaoUnderTest;
//
//    @Test
//    void testFindUserInfoByEmail() {
//        // Setup
//        final UserInfo expectedResult = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//
//        // Configure UserInfoRepo.findByEmail(...).
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockUserInfoRepo.findByEmail("email")).thenReturn(userInfo);
//
//        // Run the test
//        final UserInfo result = completeUserDaoUnderTest.findUserInfoByEmail("email");
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
//        // Configure UserInfoRepo.findByUserID(...).
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockUserInfoRepo.findByUserID(0)).thenReturn(userInfo);
//
//        // Run the test
//        final UserInfo result = completeUserDaoUnderTest.findUserInfoByUserID(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
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
//        // Configure UserInfoRepo.save(...).
//        final UserInfo userInfo1 = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        when(mockUserInfoRepo.save(
//                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)))))
//                .thenReturn(userInfo1);
//
//        // Run the test
//        final UserInfo result = completeUserDaoUnderTest.save(userInfo);
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//    }
//}
