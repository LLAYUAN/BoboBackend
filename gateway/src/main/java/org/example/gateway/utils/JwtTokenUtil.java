package org.example.gateway.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    // Claim 中的用户名
    private static final String CLAM_KEY_USERNAME = "sub";

    // Claim 中的创建时间
    private static final String CLAM_KEY_CREATED = "created";

    // JWT 密钥
    @Value("${jwt.secret}")
    private String secret;

    // JWT 过期时间
    @Value("${jwt.expiration}")
    private Long expiration;

    // Authorization 请求头中的 token 字符串的开头部分（Bearer）
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    //================ private methods ==================

    /**
     * 根据负载生成 JWT 的 token
     * @param claims 负载
     * @return JWT 的 token
     */
    private String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)  // 设置负载
                .setExpiration(generateExpirationDate())    // 设置过期时间
                .signWith(SignatureAlgorithm.HS512,secret)  // 设置签名使用的签名算法和签名使用的秘钥
                .compact();
    }

    /**
     * 生成 token 的过期时间
     * @return token 的过期时间
     */
    private Date generateExpirationDate(){
        /*
            Date 构造器接受格林威治时间，推荐使用 System.currentTimeMillis() 获取当前时间距离 1970-01-01 00:00:00 的毫秒数
            而我们在配置文件中配置的是秒数，所以需要乘以 1000。
            一般而言 Token 的过期时间为 7 天，因此我们一般在 Spring Boot 的配置文件中将 jwt.expiration 设置为 604800，
            即 7 * 24 * 60 * 60 = 604800 秒。
         */
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从 token 中获取 JWT 中的负载
     * @param token JWT 的 token
     * @return JWT 中的负载
     */
    private Claims getClaimsFromToken(String token){
        Claims claims = null;
        try{
            claims = Jwts.parser() // 解析 JWT 的 token
                    .setSigningKey(secret) // 指定签名使用的密钥（会自动推断签名的算法）
                    .parseClaimsJws(token) // 解析 JWT 的 token
                    .getBody(); // 获取 JWT 的负载（即要传输的数据）
        }catch (Exception e){
            LOGGER.info("JWT 格式验证失败：{}",token);
        }
        return claims;
    }

    /**
     * 验证 token 是否过期
     * @param token JWT 的 token
     * @return token 是否过期 true：过期 false：未过期
     */
    private boolean isTokenExpired(String token){
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从 token 中获取过期时间
     * @param token JWT 的 token
     * @return 过期时间
     */
    private Date getExpiredDateFromToken(String token){
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 判断 token 是否可以被刷新
     * @param token JWT 的 token
     * @param time 指定时间段（单位：秒）
     * @return token 是否可以被刷新 true：可以 false：不可以
     */
    private boolean tokenRefreshJustBefore(String token,int time){
        // 解析 JWT 的 token 拿到负载
        Claims claims = getClaimsFromToken(token);
        // 获取 token 的创建时间
        Date tokenCreateDate = claims.get(CLAM_KEY_CREATED, Date.class);
        // 获取当前时间
        Date refreshDate = new Date();
        // 条件1: 当前时间在 token 创建时间之后
        // 条件2: 当前时间在（token 创建时间 + 指定时间段）之前（即指定时间段内可以刷新 token）
        return refreshDate.after(tokenCreateDate) && refreshDate.before(DateUtil.offsetSecond(tokenCreateDate, time));
    }

    //================ public methods ==================

    /**
     * 从 token 中获取登录用户名
     * @param token JWT 的 token
     * @return 登录用户名
     */
    public String getUserNameFromToken(String token){
        String username;
        try{
            // 从 token 中获取 JWT 中的负载
            Claims claims = getClaimsFromToken(token);
            // 从负载中获取用户名
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    public Integer getUserIDFromHeader(String authorizedHeader){
        authorizedHeader = authorizedHeader.substring(tokenHead.length());
        return  Integer.parseInt(getUserNameFromToken(authorizedHeader));
    }

    /**
     * 验证 token 是否有效
     * @param token JWT 的 token
     * @param userDetails 从数据库中查询出来的用户信息（需要自定义 UserDetailsService 和 UserDetails）
     * @return token 是否有效 true：有效 false：无效
     */
    public boolean validateToken(String token, UserDetails userDetails){
        // 从 token 中获取用户名
        String username = getUserNameFromToken(token);
        // 条件一：用户名不为 null
        // 条件二：用户名和 UserDetails 中的用户名一致
        // 条件三：token 未过期
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 根据用户信息生成 token
     * @param userDetails 用户信息（需要自定义 UserDetails）
     * @return token 字符串
     */
    public String generateToken(UserDetails userDetails){
        // 创建负载
        Map<String,Object> claims = new HashMap<>();
        // 设置负载中的用户名
        claims.put(CLAM_KEY_USERNAME,userDetails.getUsername());
        // 设置负载中的创建时间
        claims.put(CLAM_KEY_CREATED,new Date());
        // 根据负载生成 token
        return generateToken(claims);
    }

    public String generateToken(Integer userID){
        // 创建负载
        Map<String,Object> claims = new HashMap<>();
        // 设置负载中的用户名
        claims.put(CLAM_KEY_USERNAME,userID.toString());
        // 设置负载中的创建时间
        claims.put(CLAM_KEY_CREATED,new Date());
        // 根据负载生成 token
        return generateToken(claims);
    }

    /**
     * 判断 token 是否可以被刷新
     * @param oldToken JWT 的 token
     * @return token 是否可以被刷新 true：可以 false：不可以
     */
    public String refreshHeadToken(String oldToken){
        // 失效条件1：token 为 null
        if (StrUtil.isEmpty(oldToken)) return null;
        // 失效条件2：token 格式错误（不包含 "Bearer "）
        String token = oldToken.substring(tokenHead.length());
        if (StrUtil.isEmpty(token)) return null;
        // 失效条件3：token 中没有负载
        Claims claims = getClaimsFromToken(oldToken);
        if (claims == null) return null;
        // 失效条件4：token 已过期
        if (isTokenExpired(oldToken)) return null;

        // 如果 token 在 30 分钟之内刚刷新过，返回原 token
        if (tokenRefreshJustBefore(oldToken,30*60)){
            return oldToken;
        }else { // 否则，生成新的 token
            // 设置负载中的创建时间
            claims.put(CLAM_KEY_CREATED,new Date());
            // 根据负载生成 token
            return generateToken(claims);
        }

    }
}

