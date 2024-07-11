package org.example.gateway.security;

import cn.hutool.json.JSONUtil;
import org.example.gateway.common.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 自定义认证失败处理：没有登录或 token 过期时
 */
@Component
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    /**
     * 当认证失败时，此方法会被调用
     * @param exchange WebFlux 中的服务器交换对象
     * @param authException 认证失败时抛出的异常
     * @return 一个 Mono，用于表示响应的完成状态
     */
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
        // 设置响应状态为未授权
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // 设置响应头，指示响应不应被缓存
        exchange.getResponse().getHeaders().set("Cache-Control", "no-cache");
        // 设置响应头，指示响应类型为 JSON
        exchange.getResponse().getHeaders().set("Content-Type", "application/json;charset=UTF-8");
        // 构建返回的 JSON 数据
        String body = JSONUtil.toJsonStr(CommonResult.unauthorized(authException.getMessage()));
        // 写入响应体
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(body.getBytes())));
    }
}