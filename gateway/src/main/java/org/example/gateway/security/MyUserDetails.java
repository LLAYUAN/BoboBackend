package org.example.gateway.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {
    // userID，但是转为字符串（否则没法在token中转换）
    private final String userID;

    // 用户关联的资源列表（对应 resource 资源表），本项目中已登录用户权限相同
//    private final List<> resourceList;

    // 构造注入
    public MyUserDetails(String userID) {
        this.userID = userID;
    }

    /**
     * 获取用户的权限集合
     * @return 权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 所有已登录用户都分配相同的权限角色，比如 ROLE_USER
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * 获取用户密码
     * @return 密码
     */
    @Override
    public String getPassword() {
        return "111";
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return userID;
    }

    /**
     * 账户是否未过期
     * @return true 表示未过期
     * TODO: 后续可能需要对是否哦过期做完善
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     * @return true 表示未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证（密码）是否未过期
     * @return true 表示未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否启用
     * @return true 表示启用
     */
    @Override
    public boolean isEnabled() {
        // 如果用户状态为 1，则表示启用，0 表示禁用
        return true;
//        return userInfo.getStatus().equals(1);
    }
}