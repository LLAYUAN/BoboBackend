package org.example.userservice.service;

import org.example.userservice.Feign.Feign;
import org.example.userservice.dao.CompleteUserDao;
import org.example.userservice.dao.FollowerDao;
import org.example.userservice.dao.RoomDao;
import org.example.userservice.dto.BasicUserDTO;
import org.example.userservice.entity.RoomHotIndex;
import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.repository.UserInfoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private FollowerDao mockFollowerDao;
    @Mock
    private RoomDao mockRoomDao;
    @Mock
    private UserInfoRepo mockUserInfoRepository;
    @Mock
    private CompleteUserDao mockCompleteUserDao;
    @Mock
    private Feign mockFeign;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService();
        userServiceUnderTest.followerDao = mockFollowerDao;
        userServiceUnderTest.roomDao = mockRoomDao;
        userServiceUnderTest.userInfoRepository = mockUserInfoRepository;
        userServiceUnderTest.completeUserDao = mockCompleteUserDao;
        userServiceUnderTest.feign = mockFeign;
    }

    @Test
    void testFollow() {
        // Setup
        // Run the test
        userServiceUnderTest.follow(0, 0);

        // Verify the results
        verify(mockFollowerDao).saveFollowerInfo(0, 0);
    }

    @Test
    void testUnfollow() {
        // Setup
        // Run the test
        userServiceUnderTest.unfollow(0, 0);

        // Verify the results
        verify(mockFollowerDao).deleteFollowerInfo(0, 0);
    }

    @Test
    void testGetFollowees() {
        // Setup
        final List<BasicUserDTO> expectedResult = List.of(new BasicUserDTO(0, "nickname"));
        when(mockFollowerDao.findFolloweesByFollowerID(0)).thenReturn(List.of(0));

        // Configure UserInfoRepo.findAllById(...).
        final List<UserInfo> userInfos = List.of(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1))));
        when(mockUserInfoRepository.findAllById(List.of(0))).thenReturn(userInfos);

        // Run the test
        final List<BasicUserDTO> result = userServiceUnderTest.getFollowees(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetFollowees_FollowerDaoReturnsNoItems() {
        // Setup
        final List<BasicUserDTO> expectedResult = List.of(new BasicUserDTO(0, "nickname"));
        when(mockFollowerDao.findFolloweesByFollowerID(0)).thenReturn(Collections.emptyList());

        // Configure UserInfoRepo.findAllById(...).
        final List<UserInfo> userInfos = List.of(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1))));
        when(mockUserInfoRepository.findAllById(List.of(0))).thenReturn(userInfos);

        // Run the test
        final List<BasicUserDTO> result = userServiceUnderTest.getFollowees(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetFollowees_UserInfoRepoReturnsNoItems() {
        // Setup
        when(mockFollowerDao.findFolloweesByFollowerID(0)).thenReturn(List.of(0));
        when(mockUserInfoRepository.findAllById(List.of(0))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BasicUserDTO> result = userServiceUnderTest.getFollowees(0);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetFollowers() {
        // Setup
        final List<BasicUserDTO> expectedResult = List.of(new BasicUserDTO(0, "nickname"));
        when(mockFollowerDao.findFollowersByFolloweeID(0)).thenReturn(List.of(0));

        // Configure UserInfoRepo.findAllById(...).
        final List<UserInfo> userInfos = List.of(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1))));
        when(mockUserInfoRepository.findAllById(List.of(0))).thenReturn(userInfos);

        // Run the test
        final List<BasicUserDTO> result = userServiceUnderTest.getFollowers(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetFollowers_FollowerDaoReturnsNoItems() {
        // Setup
        final List<BasicUserDTO> expectedResult = List.of(new BasicUserDTO(0, "nickname"));
        when(mockFollowerDao.findFollowersByFolloweeID(0)).thenReturn(Collections.emptyList());

        // Configure UserInfoRepo.findAllById(...).
        final List<UserInfo> userInfos = List.of(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1))));
        when(mockUserInfoRepository.findAllById(List.of(0))).thenReturn(userInfos);

        // Run the test
        final List<BasicUserDTO> result = userServiceUnderTest.getFollowers(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetFollowers_UserInfoRepoReturnsNoItems() {
        // Setup
        when(mockFollowerDao.findFollowersByFolloweeID(0)).thenReturn(List.of(0));
        when(mockUserInfoRepository.findAllById(List.of(0))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BasicUserDTO> result = userServiceUnderTest.getFollowers(0);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCheckIsFan() {
        // Setup
        when(mockFollowerDao.checkIsFan(0, 0)).thenReturn(false);

        // Run the test
        final boolean result = userServiceUnderTest.checkIsFan(0, 0);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testCheckIsFan_FollowerDaoReturnsTrue() {
        // Setup
        when(mockFollowerDao.checkIsFan(0, 0)).thenReturn(true);

        // Run the test
        final boolean result = userServiceUnderTest.checkIsFan(0, 0);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testGetFollowerCount() {
        // Setup
        when(mockFollowerDao.getFollowerCount(0)).thenReturn(0);

        // Run the test
        final Integer result = userServiceUnderTest.getFollowerCount(0);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetFolloweeCount() {
        // Setup
        when(mockFollowerDao.getFolloweeCount(0)).thenReturn(0);

        // Run the test
        final Integer result = userServiceUnderTest.getFolloweeCount(0);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testCreateRoom() {
        // Setup
        // Configure CompleteUserDao.findUserInfoByUserID(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockCompleteUserDao.findUserInfoByUserID(0)).thenReturn(userInfo);

        // Run the test
        final Integer result = userServiceUnderTest.createRoom(0, "roomName", "coverUrl", List.of(0));

        // Verify the results
        assertThat(result).isEqualTo(0);
        verify(mockRoomDao).saveRoomInfo(new RoomInfo("roomName", "description", "coverUrl"));

        // Confirm Feign.saveRoomHotIndex(...).
        final RoomHotIndex roomHotIndex = new RoomHotIndex();
        roomHotIndex.setRoomId(0);
        roomHotIndex.setDuration(0);
        roomHotIndex.setViewCount(0);
        roomHotIndex.setLikeCount(0);
        roomHotIndex.setShareCount(0);
        roomHotIndex.setConsumptionCount(0);
        roomHotIndex.setMessageCount(0);
        roomHotIndex.setNewFollowerCount(0);
        roomHotIndex.setSumViewTime(0);
        final List<RoomHotIndex> roomHotIndexList = List.of(roomHotIndex);
        verify(mockFeign).saveRoomHotIndex(roomHotIndexList);
    }
}
