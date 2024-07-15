package org.example.userservice.dto;

import lombok.Data;
import org.example.userservice.entity.RoomInfo;

@Data
public class BasicRoomDTO {
    private Integer roomID;
    private String roomName;
    private Integer[] tags;
    private String coverUrl;

    public BasicRoomDTO(Integer roomID, String roomName, Integer[] tags, String coverUrl) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.tags = tags;
        this.coverUrl = coverUrl;
    }

    public BasicRoomDTO() {
        // 开一个大小为3的tags数组，初始值都为0
        this.tags = new Integer[]{0, 0, 0};
    }

    public BasicRoomDTO(RoomInfo roomInfo) {
        this.roomID = roomInfo.getRoomID();
        this.roomName = roomInfo.getRoomName();
        // 获取tag
        this.tags = new Integer[3];
        if(roomInfo.getStudy()) this.tags[0] = 1;
        else this.tags[0] = 0;
        if(roomInfo.getEntertain()) this.tags[1] = 1;
        else this.tags[1] = 0;
        if(roomInfo.getOther()) this.tags[2] = 1;
        else this.tags[2] = 0;
        this.coverUrl = roomInfo.getCoverUrl();
    }
}
