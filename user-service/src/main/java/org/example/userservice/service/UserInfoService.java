package org.example.userservice.service;

import org.example.userservice.dao.CompleteUserDao;
import org.example.userservice.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.userservice.utils.JwtTokenUtil;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserInfoService {
    @Autowired
    private CompleteUserDao completeUserDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetails findUserByEmail(String email) {

        UserInfo userInfo = completeUserDao.findUserInfoByEmail(email);
        if(userInfo == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        else {
            String role = "ROLE_USER"; // 默认角色
            if (userInfo.getIsAdmin()) {
                role = "ROLE_ADMIN"; // 管理员角色
            }
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
            return new User(userInfo.getEmail(), userInfo.getPassword(), authorities);
        }
    }

    public String login(String email, String password) {
        Logger log = Logger.getLogger(UserInfoService.class.getName());
        String token = null;
        // 密码需要客户端加密后传递
        try{
            // 根据用户名从数据库中获取用户信息
            UserDetails userDetails = findUserByEmail(email);
            // 进行密码匹配
            if (userDetails == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            if (!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            // 封装用户信息（由于使用 JWT 进行验证，这里不需要凭证）
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            // 将用户信息存储到 Security 上下文中，以便于 Security 进行权限验证
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 生成 token
            token = JwtTokenUtil.generateToken(userDetails);
            // 添加登录记录
            log.info("用户"+email+"进行登录");
        }catch (UsernameNotFoundException | BadCredentialsException e){
            log.info("登录异常:"+e.getMessage());
        }
        return token;
    }
}
