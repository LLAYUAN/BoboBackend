//package org.example.userservice.config;
//
////import org.example.userservice.filter.JwtAuthenticationTokenFilter;
////import org.example.userservice.security.RestAuthenticationEntryPoint;
////import org.example.userservice.security.RestfulAccessDeniedHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/**").permitAll() // 允许所有请求
//                                .anyRequest().permitAll() // 允许所有其他请求
//                )
//                .csrf(csrf -> csrf.disable()) // 禁用 CSRF
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 配置 session 为无状态
//
//        return http.build();
//    }
//}
//
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig {
////
////    @Autowired
////    private IgnoreUrlsConfig ignoreUrlsConfig;
////
////    @Autowired
////    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
////
////    @Autowired
////    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
////
////    @Autowired
////    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        System.out.println("to here");
////        http
////                .authorizeHttpRequests((authz) -> authz
////                        .requestMatchers(getRequestMatchers(ignoreUrlsConfig.getUrls())).permitAll() // 配置白名单
////                        .requestMatchers(HttpMethod.OPTIONS).permitAll() // 允许所有 OPTIONS 请求
////                        .anyRequest().authenticated() // 其他请求需要认证
////                )
////                .csrf(csrf -> csrf.disable()) // 禁用 CSRF
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 配置 session 为无状态
////                .exceptionHandling(exception -> exception
////                        .accessDeniedHandler(restfulAccessDeniedHandler) // 访问被拒绝时的处理器
////                        .authenticationEntryPoint(restAuthenticationEntryPoint) // 未认证或 token 过期的处理器
////                )
////                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // 在 UsernamePasswordAuthenticationFilter 之前添加 JWT 拦截器
////
////        return http.build();
////    }
////
////    private RequestMatcher[] getRequestMatchers(List<String> urls) {
////        return urls.stream()
////                .map(RequestMatcherUtil::toRequestMatcher)
////                .toArray(RequestMatcher[]::new);
////    }
////
////    static class RequestMatcherUtil {
////        static RequestMatcher toRequestMatcher(String path) {
////            return request -> request.getServletPath().equals(path);
////        }
////    }
////}
