package org.example.userservice.config;

import org.example.userservice.service.UserInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Spring Security 相关配置
 */
@Configuration
public class MallSecurityConfig {

    // 用户信息服务（对应 user 用户表）
    private final UserInfoService userInfoService;

    // 构造注入
    public MallSecurityConfig(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // 获取登录用户信息
        return userInfoService::findUserByEmail;
    }

    // ...
}