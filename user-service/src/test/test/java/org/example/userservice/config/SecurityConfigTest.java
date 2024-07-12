//package org.example.userservice.config;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class SecurityConfigTest {
//
//    private SecurityConfig securityConfigUnderTest;
//
//    @Mock
//    private AuthenticationManagerBuilder authenticationManagerBuilder;
//
//    @Mock
//    private HttpSecurity httpSecurity; // 这里定义了模拟的 httpSecurity
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        securityConfigUnderTest = new SecurityConfig();
//    }
//
//    @Test
//    void testSecurityFilterChain() throws Exception {
//        // 配置模拟的HttpSecurity以支持链式调用
//        when(httpSecurity.authorizeRequests(any()))
//                .thenAnswer(invocation -> {
//                    // 这里不需要做实际的authorizeRequests配置，只是返回自身以支持链式调用
//                    return httpSecurity;
//                });
//        when(httpSecurity.csrf(any()))
//                .thenReturn(httpSecurity); // 同样支持链式调用
//        when(httpSecurity.sessionManagement(any()))
//                .thenReturn(httpSecurity); // 同样支持链式调用
//        when(httpSecurity.build())
//                .thenReturn(mock(DefaultSecurityFilterChain.class)); // 返回模拟的SecurityFilterChain对象
//
//        // 执行测试
//        SecurityFilterChain result = securityConfigUnderTest.securityFilterChain(httpSecurity);
//
//        // 验证结果不是null
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    void testSecurityFilterChain_ThrowsException() {
//        // Mock HttpSecurity
//        HttpSecurity http = mock(HttpSecurity.class);
//
//        // Configure necessary mocks or expectations here
//        // For example, mock any method calls on HttpSecurity that should throw an exception
//
//        // Run the test and expect an exception
//        assertThatThrownBy(() -> securityConfigUnderTest.securityFilterChain(http)).isInstanceOf(Exception.class);
//    }
//}