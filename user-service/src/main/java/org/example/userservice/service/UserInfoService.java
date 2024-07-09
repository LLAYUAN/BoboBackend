package org.example.userservice.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.userservice.dao.CompleteUserDao;
import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.repository.RoomInfoRepo;
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
    private JwtTokenUtil JwtTokenUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoomInfoRepo roomInfoRepo;

    public String login(String email, String password,UserInfo userInfo) {
        Logger log = Logger.getLogger(UserInfoService.class.getName());
        log.info("用户"+email+"进行登录");
        String token = null;
        // 密码需要客户端加密后传递
        try{
            // 根据用户名从数据库中获取用户信息
//            UserDetails userDetails = findUserByEmail(email);
            userInfo = completeUserDao.findUserInfoByEmail(email);
            // 进行密码匹配
            if (userInfo == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            if (!passwordEncoder.matches(password,userInfo.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
//            // 封装用户信息（由于使用 JWT 进行验证，这里不需要凭证）
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//            // 将用户信息存储到 Security 上下文中，以便于 Security 进行权限验证
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 生成 token
            token = JwtTokenUtil.generateToken(userInfo);
            // 添加登录记录
            log.info("用户"+email+"进行登录");
        }catch (UsernameNotFoundException | BadCredentialsException e){
            log.info("登录异常:"+e.getMessage());
        }
        return token;
    }

    public UserInfo findUserInfoByEmail(String email) {
        return completeUserDao.findUserInfoByEmail(email);
    }

    public UserInfo findUserInfoByUserID(Integer userID) {
        return completeUserDao.findUserInfoByUserID(userID);
    }

    public Integer getRoomIDByUserID(Integer userID) {
        return completeUserDao.findUserInfoByUserID(userID).getRoomInfo().getRoomID();
    }

    public RoomInfo getRoomInfoByUserID(Integer userID) {
        UserInfo userInfo = completeUserDao.findUserInfoByUserID(userID);
        RoomInfo roomInfo = userInfo.getRoomInfo();
        if(roomInfo == null){
            roomInfo = new RoomInfo("直播间", "直播间", "");
            roomInfo.setUserInfo(userInfo);
//             TODO：设置tags
            roomInfoRepo.save(roomInfo);
        }
        return roomInfo;
    }

    public UserInfo register(String email, String password) {
        // 先检查是否已经存在该用户，若存在则返回 null
        if(completeUserDao.findUserInfoByEmail(email) != null){
            return null;
        }
        UserInfo userInfo = new UserInfo(email,passwordEncoder.encode(password));
        return completeUserDao.save(userInfo);
    }

    public UserInfo save(UserInfo userInfo) {
        return completeUserDao.save(userInfo);
    }


//    public UserDetails findUserByEmail(String email) {
//        UserInfo userInfo = completeUserDao.findUserInfoByEmail(email);
//        if(userInfo == null){
//            throw new UsernameNotFoundException("用户名或密码错误");
//        }
//        else {
//            String role = "ROLE_USER"; // 默认角色
//            if (userInfo.getIsAdmin()) {
//                role = "ROLE_ADMIN"; // 管理员角色
//            }
//            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
//                return new User(userInfo.getUserID().toString(), userInfo.getPassword(), authorities);
//        }
//    }
//
//    public UserDetails findUserByUserID(String userID) {
//        UserInfo userInfo = completeUserDao.findUserInfoByUserID(Integer.parseInt(userID));
//        if(userInfo == null){
//            throw new UsernameNotFoundException("用户名或密码错误");
//        }
//        else {
//            String role = "ROLE_USER"; // 默认角色
//            if (userInfo.getIsAdmin()) {
//                role = "ROLE_ADMIN"; // 管理员角色
//            }
//            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
//            return new User(userInfo.getUserID().toString(), userInfo.getPassword(), authorities);
//        }
//    }
}
