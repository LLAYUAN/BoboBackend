package org.example.recordvideoservice.serviceimpl;

import org.example.recordvideoservice.dao.UserInfoDao;
import org.example.recordvideoservice.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceImplTest {

    @Mock
    private UserInfoDao mockUserInfoDao;

    @InjectMocks
    private UserInfoServiceImpl userInfoServiceImplUnderTest;

    @Test
    void testFindUserInfoByUserID() {
        // Setup
        final UserInfo expectedResult = new UserInfo();
        expectedResult.setUserID(0);
        expectedResult.setPassword("password");
        expectedResult.setEmail("email");
        expectedResult.setAvatarUrl("avatarUrl");
        expectedResult.setNickname("nickname");

        // Configure UserInfoDao.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo();
        userInfo.setUserID(0);
        userInfo.setPassword("password");
        userInfo.setEmail("email");
        userInfo.setAvatarUrl("avatarUrl");
        userInfo.setNickname("nickname");
        when(mockUserInfoDao.findUserInfoByUserID(0)).thenReturn(userInfo);

        // Run the test
        final UserInfo result = userInfoServiceImplUnderTest.findUserInfoByUserID(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
