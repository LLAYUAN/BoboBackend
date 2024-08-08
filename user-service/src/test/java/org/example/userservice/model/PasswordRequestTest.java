//package org.example.userservice.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//class PasswordRequestTest {
//
//    private PasswordRequest passwordRequestUnderTest;
//    private PasswordRequest ActualPasswordRequestUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        passwordRequestUnderTest = new PasswordRequest();
//        ActualPasswordRequestUnderTest = new PasswordRequest("plainPassword", "encodedPassword");
//    }
//
//    @Test
//    void testAllArgsConstructor() {
//        PasswordRequest request = new PasswordRequest("plainPassword", "encodedPassword");
//
//        assertEquals("plainPassword", request.getPlainPassword());
//        assertEquals("encodedPassword", request.getEncodedPassword());
//    }
//
//    @Test
//    void testNoArgsConstructor() {
//        PasswordRequest request = new PasswordRequest();
//
//        assertNull(request.getPlainPassword());
//        assertNull(request.getEncodedPassword());
//    }
//
//    @Test
//    void testSetterAndGetters() {
//        passwordRequestUnderTest.setPlainPassword("newPlainPassword");
//        passwordRequestUnderTest.setEncodedPassword("newEncodedPassword");
//
//        assertEquals("newPlainPassword", passwordRequestUnderTest.getPlainPassword());
//        assertEquals("newEncodedPassword", passwordRequestUnderTest.getEncodedPassword());
//    }
//
//    @Test
//    void testToString() {
//        passwordRequestUnderTest.setPlainPassword("testPlainPassword");
//        passwordRequestUnderTest.setEncodedPassword("testEncodedPassword");
//
//        String expectedToString = "PasswordRequest(plainPassword=testPlainPassword, encodedPassword=testEncodedPassword)";
//        assertEquals(expectedToString, passwordRequestUnderTest.toString());
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        PasswordRequest sameRequest = new PasswordRequest("plainPassword", "encodedPassword");
//
//        assertEquals(sameRequest, ActualPasswordRequestUnderTest);
//        assertEquals(sameRequest.hashCode(), ActualPasswordRequestUnderTest.hashCode());
//
//        PasswordRequest differentRequest = new PasswordRequest("differentPlainPassword", "differentEncodedPassword");
//
//        assertNotEquals(differentRequest, ActualPasswordRequestUnderTest);
//    }
//}
