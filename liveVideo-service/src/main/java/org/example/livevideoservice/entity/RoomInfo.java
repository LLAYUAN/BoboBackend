package org.example.livevideoservice.entity;

import cn.hutool.system.UserInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room_info")
public class RoomInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomID; // 这是主键字段

    @Column(name = "name")
    private String roomName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "study")
    private Boolean study;

    @Column(name = "entertain")
    private Boolean entertain;

    @Column(name = "other")
    private Boolean other;

    public RoomInfo(String roomName, String description, String coverUrl) {
        this.roomName = roomName;
        this.description = description;
        this.coverUrl = coverUrl;
        this.status = true;
        this.study = false;
        this.entertain = false;
        this.other = false;
    }
}
