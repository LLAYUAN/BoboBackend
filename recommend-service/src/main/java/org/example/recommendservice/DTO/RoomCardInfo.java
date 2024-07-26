package org.example.recommendservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.recommendservice.entity.RoomHotIndex;
import org.example.recommendservice.entity.RoomInfo;
import org.example.recommendservice.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCardInfo {
    private int id;
    private int userID;
    private String roomName;
    private String description;
    private String coverUrl;
    private String userName;
    private String userDescription;
    private String avatarUrl;
    private List<Boolean> tags;
    private int hotIndex;

    public RoomCardInfo(RoomInfo roomInfo, RoomHotIndex roomHotIndex) {
        this.id = roomInfo.getRoomID();
        this.userID = roomInfo.getUserInfo().getUserID();
        this.roomName = roomInfo.getRoomName();
        this.description = roomInfo.getDescription();
        this.coverUrl = roomInfo.getCoverUrl();
        this.userName = roomInfo.getUserInfo().getNickname();
        this.userDescription = roomInfo.getUserInfo().getSelfIntro();
        this.avatarUrl = roomInfo.getUserInfo().getAvatarUrl();
        this.tags = roomHotIndex.getTags();
        this.hotIndex = roomHotIndex.getHotIndex();
    }
}
