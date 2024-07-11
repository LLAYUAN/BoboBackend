package org.example.userservice.dto;

import lombok.Data;
import org.example.userservice.entity.UserInfo;

@Data
public class BasicUserDTO {
    public Integer userID;
    public String nickname;
//    public String avatarUrl;

    public BasicUserDTO(Integer userID, String nickname) {
        this.userID = userID;
        this.nickname = nickname;
//        this.avatarUrl = avatarUrl;
    }

    public BasicUserDTO() {
    }

    public BasicUserDTO(UserInfo userInfo) {
        this.userID = userInfo.getUserID();
        this.nickname = userInfo.getNickname();
//        this.avatarUrl = userInfo.getAvatarUrl();
    }
}
