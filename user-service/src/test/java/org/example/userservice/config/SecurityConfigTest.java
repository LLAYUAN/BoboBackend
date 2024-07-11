package org.example.userservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecurityConfigTest {

    private SecurityConfig securityConfigUnderTest;

    @BeforeEach
    void setUp() {
        securityConfigUnderTest = new SecurityConfig();
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        // Setup
        final HttpSecurity http = new HttpSecurity(null, new AuthenticationManagerBuilder(null),
                Map.ofEntries(Map.entry(String.class, "value")));

        // Run the test
        final SecurityFilterChain result = securityConfigUnderTest.securityFilterChain(http);

        // Verify the results
    }

    @Test
    void testSecurityFilterChain_ThrowsException() {
        // Setup
        final HttpSecurity http = new HttpSecurity(null, new AuthenticationManagerBuilder(null),
                Map.ofEntries(Map.entry(String.class, "value")));

        // Run the test
        assertThatThrownBy(() -> securityConfigUnderTest.securityFilterChain(http)).isInstanceOf(Exception.class);
    }
}
