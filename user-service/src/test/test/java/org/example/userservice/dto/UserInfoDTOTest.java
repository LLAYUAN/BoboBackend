package org.example.userservice.dto;

import org.example.userservice.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserInfoDTOTest {

    @Mock
    private UserInfo mockUserInfo;

    private UserInfoDTO userInfoDTOUnderTest;

    @BeforeEach
    void setUp() {
        userInfoDTOUnderTest = new UserInfoDTO(mockUserInfo, 0, 0, false);
    }
}
