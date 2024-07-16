package org.example.userservice.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import org.example.userservice.Feign.Feign;
import org.example.userservice.dao.CompleteUserDao;
import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.model.PasswordRequest;
import org.example.userservice.repository.RoomInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.example.userservice.utils.JwtTokenUtil;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserInfoService {
    @Autowired
    private CompleteUserDao completeUserDao;

//    @Autowired
//    private JwtTokenUtil JwtTokenUtil;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoomInfoRepo roomInfoRepo;

    @Autowired
    private Feign feign;

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
                throw new Exception("用户不存在");
            }
//            System.out.println("feign encoder: "+feign.encode(password));
//            System.out.println("local encoder: "+passwordEncoder.encode(password));
            if(!feign.matchPassword(new PasswordRequest(password,userInfo.getPassword()))){
                throw new Exception("密码不正确");
            }
//            if (!passwordEncoder.matches(password,userInfo.getPassword())){
//                throw new BadCredentialsException("密码不正确");
//            }
//            // 封装用户信息（由于使用 JWT 进行验证，这里不需要凭证）
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//            // 将用户信息存储到 Security 上下文中，以便于 Security 进行权限验证
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 生成 token
            token = feign.generateToken(userInfo.getUserID());
            // 添加登录记录
            log.info("用户"+email+"进行登录");
        }catch (Exception e){
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
            roomInfo = new RoomInfo("直播间", "视频简介", "");
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
//        UserInfo userInfo = new UserInfo(email,passwordEncoder.encode(password));
        UserInfo userInfo = new UserInfo(email,feign.encode(password));
        return completeUserDao.save(userInfo);
    }

    public UserInfo save(UserInfo userInfo) {
        return completeUserDao.save(userInfo);
    }

    public Boolean modifyPassword(Integer userID,String oldPassword,String newPassword){
        UserInfo userInfo = completeUserDao.findUserInfoByUserID(userID);
//        if(passwordEncoder.matches(oldPassword,userInfo.getPassword())){
//            userInfo.setPassword(passwordEncoder.encode(newPassword));
//            completeUserDao.save(userInfo);
//            return true;
//        }
        if(feign.matchPassword(new PasswordRequest(oldPassword,userInfo.getPassword()))){
            userInfo.setPassword(feign.encode(newPassword));
            completeUserDao.save(userInfo);
            return true;
        }
        return false;
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
