package org.example.loginservice.service;

import org.example.loginservice.dao.UserInfoDao;
import org.example.loginservice.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    public UserDetails findUserByEmail(String email) {
        UserInfo userInfo = userInfoDao.findByEmail(email);
        String role = "ROLE_USER"; // 默认角色
        if(userInfo.getIsAdmin()){
            role = "ROLE_ADMIN"; // 管理员角色
        }
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        return new User(userInfo.getEmail(), userInfo.getPassword(), authorities);
    }

}
