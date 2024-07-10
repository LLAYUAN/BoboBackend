package org.example.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.example.userservice.entity.UserInfo;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore all null fields in JSON serialization
public class UserInfoDTO {
    private Integer userID;
    private String nickname;
    private String avatarUrl;
    private String birthday;
    private String introduction;
    private String email;
    private Integer followerCount;
    private Integer followeeCount;
    private Boolean isFan;

    public UserInfoDTO() {
        this.nickname = "";
        this.avatarUrl = "";
        this.birthday = "";
        this.introduction = "";
        this.email = "";
        this.followeeCount = null;
        this.followerCount = null;
        this.isFan = null;
    }

    public UserInfoDTO(UserInfo userInfo) {
        this.userID = userInfo.getUserID();
        if(userInfo.getNickname() == null) {
            this.nickname = "";
        }
        else {
            this.nickname = userInfo.getNickname();
        }
        this.avatarUrl = userInfo.getAvatarUrl();
        if(userInfo.getBirthday() == null) {
            this.birthday = "";
        }
        else {
            this.birthday = userInfo.getBirthday().toString();
        }
        if (userInfo.getSelfIntro() == null) {
            this.introduction = "";
        }
        else {
            this.introduction = userInfo.getSelfIntro();
        }
        this.email = userInfo.getEmail();
    }

    public UserInfoDTO(UserInfo userInfo,Integer followeeCount,Integer followerCount,boolean isFan){
        this.userID = userInfo.getUserID();
        if(userInfo.getNickname() == null) {
            this.nickname = "";
        }
        else {
            this.nickname = userInfo.getNickname();
        }
        this.avatarUrl = userInfo.getAvatarUrl();
        if(userInfo.getBirthday() == null) {
            this.birthday = "";
        }
        else {
            this.birthday = userInfo.getBirthday().toString();
        }
        if (userInfo.getSelfIntro() == null) {
            this.introduction = "";
        }
        else {
            this.introduction = userInfo.getSelfIntro();
        }
        this.email = userInfo.getEmail();
        this.followeeCount = followeeCount;
        this.followerCount = followerCount;
        this.isFan = isFan;
    }

    public UserInfoDTO(Integer userID, String nickname){
        this.userID = userID;
        if(nickname == null) {
            this.nickname = "";
        }
        else {
            this.nickname = nickname;
        }
//        this.avatarUrl = "";
//        this.birthday = "";
//        this.introduction = "";
//        this.email = "";
    }
}
