package org.example.gateway.config;

import org.example.gateway.filter.JwtAuthenticationTokenFilter;
import org.example.gateway.security.RestAuthenticationEntryPoint;
import org.example.gateway.security.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        System.out.println("to here");
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers(getRequestMatchers(ignoreUrlsConfig.getUrls())).permitAll() // 配置白名单
//                        .requestMatchers(HttpMethod.OPTIONS).permitAll() // 允许所有 OPTIONS 请求
//                        .anyRequest().authenticated() // 其他请求需要认证
//                )
//                .csrf(csrf -> csrf.disable()) // 禁用 CSRF
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 配置 session 为无状态
//                .exceptionHandling(exception -> exception
//                        .accessDeniedHandler(restfulAccessDeniedHandler) // 访问被拒绝时的处理器
//                        .authenticationEntryPoint(restAuthenticationEntryPoint) // 未认证或 token 过期的处理器
//                )
//                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // 在 UsernamePasswordAuthenticationFilter 之前添加 JWT 拦截器
//
//        return http.build();
//    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // 设置白名单和认证规则
        return http
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers(ignoreUrlsConfig.getUrls().toArray(new String[0])).permitAll() // 配置白名单
                                .pathMatchers(HttpMethod.OPTIONS).permitAll() // 允许所有 OPTIONS 请求
                                .anyExchange().authenticated() // 其他请求需要认证
                )
                .csrf(csrf -> csrf.disable()) // 禁用 CSRF
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(restfulAccessDeniedHandler) // 访问被拒绝时的处理器
                        .authenticationEntryPoint(restAuthenticationEntryPoint) // 未认证或 token 过期的处理器
                )
                .addFilterAt(jwtAuthenticationTokenFilter, SecurityWebFiltersOrder.AUTHENTICATION) // 添加 JWT 拦截器到过滤链
//                .securityContextRepository(securityContextRepository) // 设置安全上下文库
                .build();
    }

    private RequestMatcher[] getRequestMatchers(List<String> urls) {
        return urls.stream()
                .map(RequestMatcherUtil::toRequestMatcher)
                .toArray(RequestMatcher[]::new);
    }

    static class RequestMatcherUtil {
        static RequestMatcher toRequestMatcher(String path) {
            return request -> request.getServletPath().equals(path);
        }
    }
}