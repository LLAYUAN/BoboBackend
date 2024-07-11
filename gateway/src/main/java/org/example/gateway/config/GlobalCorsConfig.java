//package org.example.gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
///**
// * 全局跨域配置
// */
//@Configuration
//public class GlobalCorsConfig {
//
//    /**
//     * 配置跨域访问的过滤器
//     * @return 返回配置好的跨域过滤器
//     */
//    @Bean
//    public CorsFilter corsFilter(){
//        // 创建 CORS 配置对象
//        CorsConfiguration config = new CorsConfiguration();
//
//        // 允许任何域名使用
//        config.addAllowedOriginPattern("*");
//
//        // 允许客户端携带凭证
//        config.setAllowCredentials(true);
//
//        // 允许所有的请求头
//        config.addAllowedHeader("*");
//
//        // 允许所有的请求方法（主要是跨域的 OPTIONS 预检请求）
//        config.addAllowedMethod("*");
//
//        // 创建 CORS 配置源对象
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        // 为所有的 URL 路径设置跨域配置
//        source.registerCorsConfiguration("/**",config);
//
//        // 返回新的 CORS 过滤器，使用上面的配置源
//        return new CorsFilter(source);
//    }
//}
//
