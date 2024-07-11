package org.example.userservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordEncoderConfigTest {

    private PasswordEncoderConfig passwordEncoderConfigUnderTest;

    @BeforeEach
    void setUp() {
        passwordEncoderConfigUnderTest = new PasswordEncoderConfig();
    }

    @Test
    void testPasswordEncoder() {
        // Setup
        // Run the test
        final BCryptPasswordEncoder result = passwordEncoderConfigUnderTest.passwordEncoder();

        // Verify the results
    }
}
