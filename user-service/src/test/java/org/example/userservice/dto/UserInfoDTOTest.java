//package org.example.userservice.dto;
//
//import org.example.userservice.entity.UserInfo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class UserInfoDTOTest {
//
//    private UserInfoDTO userInfoDTOUnderTest_complete;
//    private UserInfoDTO userInfoDTOUnderTest_brief;
//    private UserInfo mockUserInfo;
//
//    @BeforeEach
//    void setUp() {
//        mockUserInfo = mock(UserInfo.class);
//        userInfoDTOUnderTest_complete = new UserInfoDTO(mockUserInfo, 0, 0, false);
//        userInfoDTOUnderTest_brief = new UserInfoDTO(0, "testNickname", "test@example.com");
////        userInfoDTOUnderTest.setUserID(0);
////        userInfoDTOUnderTest.setNickname("testNickname");
////        userInfoDTOUnderTest.setBirthday(null);
////        userInfoDTOUnderTest.setEmail("test@example.com");
////        userInfoDTOUnderTest.setAvatarUrl("testAvatarUrl");
//    }
//
//
//    @Test
//    void testConstructorWithUserInfo() {
//        // Set up mock UserInfo
//        when(mockUserInfo.getUserID()).thenReturn(1);
//        when(mockUserInfo.getNickname()).thenReturn("testNickname");
//        when(mockUserInfo.getAvatarUrl()).thenReturn("testAvatarUrl");
//        when(mockUserInfo.getBirthday()).thenReturn(Date.valueOf("1990-01-01")); // Assuming getBirthday() returns String
//        when(mockUserInfo.getSelfIntro()).thenReturn("Test introduction");
//        when(mockUserInfo.getEmail()).thenReturn("test@example.com");
//
//        UserInfoDTO userDTO = new UserInfoDTO(mockUserInfo);
//
//        assertThat(userDTO.getUserID()).isEqualTo(1);
//        assertThat(userDTO.getNickname()).isEqualTo("testNickname");
//        assertThat(userDTO.getAvatarUrl()).isEqualTo("testAvatarUrl");
//        assertThat(userDTO.getBirthday()).isEqualTo("1990-01-01");
//        assertThat(userDTO.getIntroduction()).isEqualTo("Test introduction");
//        assertThat(userDTO.getEmail()).isEqualTo("test@example.com");
//        assertThat(userDTO.getFolloweeCount()).isNull();
//        assertThat(userDTO.getFollowerCount()).isNull();
//        assertThat(userDTO.getIsFan()).isNull();
//    }
//
//    @Test
//    void testConstructorWithAllParameters() {
//        // Set up mock UserInfo
//        when(mockUserInfo.getUserID()).thenReturn(1);
//        when(mockUserInfo.getNickname()).thenReturn("testNickname");
//        when(mockUserInfo.getAvatarUrl()).thenReturn("testAvatarUrl");
//        when(mockUserInfo.getBirthday()).thenReturn(Date.valueOf("1990-01-01"));
//        when(mockUserInfo.getSelfIntro()).thenReturn("Test introduction");
//        when(mockUserInfo.getEmail()).thenReturn("test@example.com");
//
//        UserInfoDTO userDTO = new UserInfoDTO(mockUserInfo, 10, 20, true);
//
//        assertThat(userDTO.getUserID()).isEqualTo(1);
//        assertThat(userDTO.getNickname()).isEqualTo("testNickname");
//        assertThat(userDTO.getAvatarUrl()).isEqualTo("testAvatarUrl");
//        assertThat(userDTO.getBirthday()).isEqualTo("1990-01-01");
//        assertThat(userDTO.getIntroduction()).isEqualTo("Test introduction");
//        assertThat(userDTO.getEmail()).isEqualTo("test@example.com");
//        assertThat(userDTO.getFolloweeCount()).isEqualTo(10);
//        assertThat(userDTO.getFollowerCount()).isEqualTo(20);
//        assertThat(userDTO.getIsFan()).isTrue();
//    }
//
//    @Test
//    void testConstructorWithUserIDNicknameAndEmail() {
//        UserInfoDTO userDTO = new UserInfoDTO(1, "testNickname", "test@example.com");
//
//        assertThat(userDTO.getUserID()).isEqualTo(1);
//        assertThat(userDTO.getNickname()).isEqualTo("testNickname");
//        assertThat(userDTO.getEmail()).isEqualTo("test@example.com");
//        assertThat(userDTO.getAvatarUrl()).isNull(); // Ensure avatarUrl is empty
//        assertThat(userDTO.getBirthday()).isNull(); // Ensure birthday is empty
//        assertThat(userDTO.getIntroduction()).isNull(); // Ensure introduction is empty
//        assertThat(userDTO.getFolloweeCount()).isNull(); // Ensure followeeCount is null
//        assertThat(userDTO.getFollowerCount()).isNull(); // Ensure followerCount is null
//        assertThat(userDTO.getIsFan()).isNull(); // Ensure isFan is null
//    }
//
//    @Test
//    void testConstructorWithUserIDNicknameAndEmail_nullNickname() {
//        UserInfoDTO userDTO = new UserInfoDTO(1, null, "test@example.com");
//
//        assertThat(userDTO.getUserID()).isEqualTo(1);
//        assertThat(userDTO.getNickname()).isEqualTo("");
//        assertThat(userDTO.getEmail()).isEqualTo("test@example.com");
//        assertThat(userDTO.getAvatarUrl()).isNull(); // Ensure avatarUrl is empty
//        assertThat(userDTO.getBirthday()).isNull(); // Ensure birthday is empty
//        assertThat(userDTO.getIntroduction()).isNull(); // Ensure introduction is empty
//        assertThat(userDTO.getFolloweeCount()).isNull(); // Ensure followeeCount is null
//        assertThat(userDTO.getFollowerCount()).isNull(); // Ensure followerCount is null
//        assertThat(userDTO.getIsFan()).isNull(); // Ensure isFan is null
//    }
//
//    @Test
//    void testConstructorWithUserIDNicknameAndEmail_nullEmail() {
//        UserInfoDTO userDTO = new UserInfoDTO(1, "testNickname", null);
//
//        assertThat(userDTO.getUserID()).isEqualTo(1);
//        assertThat(userDTO.getNickname()).isEqualTo("testNickname");
//        assertThat(userDTO.getEmail()).isEqualTo("");
//        assertThat(userDTO.getAvatarUrl()).isNull(); // Ensure avatarUrl is empty
//        assertThat(userDTO.getBirthday()).isNull(); // Ensure birthday is empty
//        assertThat(userDTO.getIntroduction()).isNull(); // Ensure introduction is empty
//        assertThat(userDTO.getFolloweeCount()).isNull(); // Ensure followeeCount is null
//        assertThat(userDTO.getFollowerCount()).isNull(); // Ensure followerCount is null
//        assertThat(userDTO.getIsFan()).isNull(); // Ensure isFan is null
//    }
//
//    @Test
//    void testDefaultConstructor() {
//        UserInfoDTO defaultDTO = new UserInfoDTO();
//
//        assertThat(defaultDTO.getUserID()).isNull();
//        assertThat(defaultDTO.getNickname()).isEmpty();
//        assertThat(defaultDTO.getAvatarUrl()).isEmpty();
//        assertThat(defaultDTO.getBirthday()).isEmpty();
//        assertThat(defaultDTO.getIntroduction()).isEmpty();
//        assertThat(defaultDTO.getEmail()).isEmpty();
//        assertThat(defaultDTO.getFolloweeCount()).isNull();
//        assertThat(defaultDTO.getFollowerCount()).isNull();
//        assertThat(defaultDTO.getIsFan()).isNull();
//    }
//
//    @Test
//    void testConstructorWithNullValues() {
//        // Test case 1: UserInfo with all null values
//        when(mockUserInfo.getUserID()).thenReturn(1);
//        when(mockUserInfo.getNickname()).thenReturn(null);
//        when(mockUserInfo.getAvatarUrl()).thenReturn(null);
//        when(mockUserInfo.getBirthday()).thenReturn(null);
//        when(mockUserInfo.getSelfIntro()).thenReturn(null);
//        when(mockUserInfo.getEmail()).thenReturn(null);
//
//        UserInfoDTO userDTO = new UserInfoDTO(mockUserInfo);
//
//        assertThat(userDTO.getUserID()).isEqualTo(1);
//        assertThat(userDTO.getNickname()).isEmpty();
//        assertThat(userDTO.getAvatarUrl()).isNull();
//        assertThat(userDTO.getBirthday()).isEmpty();
//        assertThat(userDTO.getIntroduction()).isEmpty();
//        assertThat(userDTO.getEmail()).isNull();
//        assertThat(userDTO.getFolloweeCount()).isNull();
//        assertThat(userDTO.getFollowerCount()).isNull();
//        assertThat(userDTO.getIsFan()).isNull();
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        UserInfoDTO sameDTO = new UserInfoDTO(0, "testNickname", "test@example.com");
//        UserInfoDTO differentDTO = new UserInfoDTO(2, "differentNickname", "different@example.com");
//
//        assertThat(userInfoDTOUnderTest_brief).isEqualTo(sameDTO);
//        assertThat(userInfoDTOUnderTest_brief.hashCode()).isEqualTo(sameDTO.hashCode());
//        assertThat(userInfoDTOUnderTest_brief).isNotEqualTo(differentDTO);
//    }
//
//    @Test
//    void testToString() {
//        String expectedToString = "UserInfoDTO(userID=0, nickname=testNickname, avatarUrl=null, birthday=null, introduction=null, email=test@example.com, followerCount=null, followeeCount=null, isFan=null)";
//        assertThat(userInfoDTOUnderTest_brief.toString()).isEqualTo(expectedToString);
//    }
//}
