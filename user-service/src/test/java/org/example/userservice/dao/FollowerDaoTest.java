//package org.example.userservice.dao;
//
//import org.example.userservice.entity.FollowerInfo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//
//import static javax.management.Query.eq;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//
//
//@ExtendWith(MockitoExtension.class)
//class FollowerDaoTest {
//
//    @Mock
//    private MongoTemplate mockMongoTemplate;
//
//    @InjectMocks
//    private FollowerDao followerDaoUnderTest;
//
//    @Test
//    void testSaveFollowerInfo() {
//        // Setup
//        Query query = new Query(Criteria.where("followeeID").is(0).and("followerID").is(0));
//        when(mockMongoTemplate.exists(eq(query), eq(FollowerInfo.class))).thenReturn(false);
//
//        // Create the expected FollowerInfo object with the correct followTime
//        LocalDateTime expectedFollowTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
//        FollowerInfo expectedFollowerInfo = new FollowerInfo("id", 0, 0, expectedFollowTime);
//
//        // Run the test
//        followerDaoUnderTest.saveFollowerInfo(0, 0);
//
//        // Verify the results
//        ArgumentCaptor<FollowerInfo> captor = ArgumentCaptor.forClass(FollowerInfo.class);
//        verify(mockMongoTemplate).save(captor.capture());
//
//        FollowerInfo savedFollowerInfo = captor.getValue();
//        assertEquals(expectedFollowerInfo.getFollowerID(), savedFollowerInfo.getFollowerID());
//        assertEquals(expectedFollowerInfo.getFolloweeID(), savedFollowerInfo.getFolloweeID());
////        assertEquals(expectedFollowerInfo.getFollowTime(), savedFollowerInfo.getFollowTime());
//    }
//
//
//
//    @Test
//    void testSaveFollowerInfo_MongoTemplateExistsReturnsTrue() {
//        // Setup
//        Query query = new Query(Criteria.where("followeeID").is(0).and("followerID").is(0));
//        when(mockMongoTemplate.exists(eq(query), eq(FollowerInfo.class))).thenReturn(true);
//
//        // Run the test
//        assertThatThrownBy(() -> followerDaoUnderTest.saveFollowerInfo(0, 0)).isInstanceOf(RuntimeException.class);
//    }
//
//    @Test
//    void testDeleteFollowerInfo() {
//        // Setup
//        Query query = new Query(Criteria.where("followeeID").is(0).and("followerID").is(0));
//        when(mockMongoTemplate.exists(any(Query.class), eq(FollowerInfo.class))).thenReturn(true);
//
//        // Run the test
//        followerDaoUnderTest.deleteFollowerInfo(0, 0);
//
//        // Verify the results
//        verify(mockMongoTemplate).remove(eq(query), eq(FollowerInfo.class));
//    }
//
//
//    @Test
//    void testDeleteFollowerInfo_MongoTemplateExistsReturnsFalse() {
//        // Setup
//        Query query = new Query(Criteria.where("followeeID").is(0).and("followerID").is(0));
//        when(mockMongoTemplate.exists(any(Query.class), eq(FollowerInfo.class))).thenReturn(false);
//
//        // Run the test
//        assertThatThrownBy(() -> followerDaoUnderTest.deleteFollowerInfo(0, 0)).isInstanceOf(RuntimeException.class);
//    }
//
//    @Test
//    void testFindFolloweesByFollowerID() {
//        // Setup
//        // Configure MongoTemplate.find(...).
//        Query query = new Query(Criteria.where("followeeID").is(0).and("followerID").is(0));
//        final List<FollowerInfo> followerInfos = List.of(
//                new FollowerInfo("id", 0, 0, LocalDateTime.of(2020, 1, 1, 0, 0)));
//        when(mockMongoTemplate.find(any(Query.class), eq(FollowerInfo.class))).thenReturn(followerInfos);
//
//        // Run the test
//        final List<Integer> result = followerDaoUnderTest.findFolloweesByFollowerID(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(List.of(0));
//    }
//
//
//    @Test
//    void testFindFolloweesByFollowerID_MongoTemplateReturnsNoItems() {
//        // Setup
//        when(mockMongoTemplate.find(any(Query.class), eq(FollowerInfo.class))).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final List<Integer> result = followerDaoUnderTest.findFolloweesByFollowerID(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(Collections.emptyList());
//    }
//
//    @Test
//    void testFindFollowersByFolloweeID() {
//        // Setup
//        // Configure MongoTemplate.find(...).
//        final List<FollowerInfo> followerInfos = List.of(
//                new FollowerInfo("id", 0, 0, LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
//        when(mockMongoTemplate.find(any(Query.class), eq(FollowerInfo.class))).thenReturn(followerInfos);
//
//        // Run the test
//        final List<Integer> result = followerDaoUnderTest.findFollowersByFolloweeID(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(List.of(0));
//    }
//
//    @Test
//    void testFindFollowersByFolloweeID_MongoTemplateReturnsNoItems() {
//        // Setup
//        when(mockMongoTemplate.find(any(Query.class), eq(FollowerInfo.class))).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final List<Integer> result = followerDaoUnderTest.findFollowersByFolloweeID(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(Collections.emptyList());
//    }
//
//    @Test
//    void testCheckIsFan() {
//        // Setup
//        when(mockMongoTemplate.exists(any(Query.class), eq(FollowerInfo.class))).thenReturn(false);
//
//        // Run the test
//        final boolean result = followerDaoUnderTest.checkIsFan(0, 0);
//
//        // Verify the results
//        assertThat(result).isFalse();
//    }
//
//    @Test
//    void testCheckIsFan_MongoTemplateReturnsTrue() {
//        // Setup
//        when(mockMongoTemplate.exists(any(Query.class), eq(FollowerInfo.class))).thenReturn(true);
//
//        // Run the test
//        final boolean result = followerDaoUnderTest.checkIsFan(0, 0);
//
//        // Verify the results
//        assertThat(result).isTrue();
//    }
//
//    @Test
//    void testGetFollowerCount() {
//        // Setup
//        when(mockMongoTemplate.count(any(Query.class), eq(FollowerInfo.class))).thenReturn(0L);
//
//        // Run the test
//        final Integer result = followerDaoUnderTest.getFollowerCount(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(0);
//    }
//
//    @Test
//    void testGetFolloweeCount() {
//        // Setup
//        when(mockMongoTemplate.count(any(Query.class), eq(FollowerInfo.class))).thenReturn(0L); // 修正这里的 Query 对象，确保有效的查询条件
//
//        // Run the test
//        final Integer result = followerDaoUnderTest.getFolloweeCount(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo(0);
//    }
//}
