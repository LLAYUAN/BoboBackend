package org.example.userservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FollowerInfoTest {

    private FollowerInfo followerInfoUnderTest;

    @BeforeEach
    void setUp() {
        followerInfoUnderTest = new FollowerInfo("id", 0, 0, LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    }

    @Test
    void testConstructor() {
        assertEquals("id", followerInfoUnderTest.getId());
        assertEquals(0, followerInfoUnderTest.getFollowerID());
        assertEquals(0, followerInfoUnderTest.getFolloweeID());
        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0, 0), followerInfoUnderTest.getFollowTime());
    }

    @Test
    void testSetterAndGetters() {
        followerInfoUnderTest.setId("newId");
        followerInfoUnderTest.setFollowerID(1);
        followerInfoUnderTest.setFolloweeID(2);
        followerInfoUnderTest.setFollowTime(LocalDateTime.of(2021, 1, 1, 0, 0, 0));

        assertEquals("newId", followerInfoUnderTest.getId());
        assertEquals(1, followerInfoUnderTest.getFollowerID());
        assertEquals(2, followerInfoUnderTest.getFolloweeID());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), followerInfoUnderTest.getFollowTime());
    }

    @Test
    void testToString() {
        String expectedToString = "FollowerInfo(id=id, followerID=0, followeeID=0, followTime=2020-01-01T00:00)";
        assertEquals(expectedToString, followerInfoUnderTest.toString());
    }

    @Test
    void testHashCode() {
        FollowerInfo sameInfo = new FollowerInfo("id", 0, 0, LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        assertEquals(sameInfo.hashCode(), followerInfoUnderTest.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        FollowerInfo emptyInfo = new FollowerInfo();
        assertEquals(null, emptyInfo.getId());
        assertEquals(null, emptyInfo.getFollowerID());
        assertEquals(null, emptyInfo.getFolloweeID());
        assertEquals(null, emptyInfo.getFollowTime());
    }
}
