//package org.example.userservice.utils;
//
//import org.example.userservice.entity.UserInfo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.sql.Date;
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class JwtTokenUtilTest {
//
//    private JwtTokenUtil jwtTokenUtilUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        jwtTokenUtilUnderTest = new JwtTokenUtil();
//        ReflectionTestUtils.setField(jwtTokenUtilUnderTest, "secret", "secret");
//        ReflectionTestUtils.setField(jwtTokenUtilUnderTest, "expiration", 0L);
//        ReflectionTestUtils.setField(jwtTokenUtilUnderTest, "tokenHead", "tokenHead");
//    }
//
//    @Test
//    void testGenerateToken2() {
//        // Setup
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//
//        // Run the test
//        final String result = jwtTokenUtilUnderTest.generateToken(userInfo);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }
//
//    @Test
//    void testRefreshHeadToken() {
//        // Setup
//        // Run the test
//        final String result = jwtTokenUtilUnderTest.refreshHeadToken("oldToken");
//
//        // Verify the results
//        assertThat(result).isEqualTo("oldToken");
//    }
//}
