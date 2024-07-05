package org.example.gateway.filter;

import org.example.gateway.security.MyUserDetails;
import org.example.gateway.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Collectors;

//@Component
public class JwtAuthenticationTokenFilter implements WebFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authToken = null;
        String path = exchange.getRequest().getPath().value();
        if (path.startsWith("/message/chat")) {// 如果是websocket请求
            ServerHttpRequest request = exchange.getRequest();
            authToken = request.getQueryParams().getFirst("token");

            URI uri = request.getURI();
            String newQuery = uri.getQuery().lines()
                    .filter(param -> !param.startsWith("token="))
                    .collect(Collectors.joining("&"));

            URI newUri = null;
            try {
                newUri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), newQuery, uri.getFragment());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            ServerHttpRequest modifiedRequest = request.mutate().uri(newUri).build();
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
        } else {
            String authHeader = exchange.getRequest().getHeaders().getFirst(this.tokenHeader);
            if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
                authToken = Objects.requireNonNull(authHeader).substring(this.tokenHead.length());
            }
        }
        if (authToken != null) {

            String username = jwtTokenUtil.getUserNameFromToken(authToken);

            System.out.println("username: " + username);

            LOGGER.info("authToken: {}", authToken);
            LOGGER.info("checking username: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = new MyUserDetails(username);

                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);

                    // 将userID的string格式替换请求头中的authorization
                    exchange.getRequest().mutate().header(this.tokenHeader, username).build();
//                    exchange.getAttributes().put("userID", Integer.parseInt(username));

                    return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                }
            }

        }

        return chain.filter(exchange);
    }

}
