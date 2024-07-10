package org.example.recommendservice.DTO;

import lombok.Data;
import org.example.recommendservice.entity.RoomInfo;
import org.example.recommendservice.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomCardInfo {
    private int id;
    private String roomName;
    private String description;
    private String coverUrl;
    private String userName;
    private String userDescription;
    private String avatarUrl;
    private List<Boolean> tags;
    private int hotIndex;

    public RoomCardInfo(RoomInfo roomInfo) {
        this.id = roomInfo.getRoomID();
        this.roomName = roomInfo.getRoomName();
        this.description = roomInfo.getDescription();
        this.coverUrl = roomInfo.getCoverUrl();
        this.userName = roomInfo.getUserInfo().getNickname();
        this.userDescription = roomInfo.getUserInfo().getSelfIntro();
        this.avatarUrl = roomInfo.getUserInfo().getAvatarUrl();
        this.tags = new ArrayList<>(3);
        this.tags.add(roomInfo.getStudy());
        this.tags.add(roomInfo.getEntertain());
        this.tags.add(roomInfo.getOther());
    }
}
