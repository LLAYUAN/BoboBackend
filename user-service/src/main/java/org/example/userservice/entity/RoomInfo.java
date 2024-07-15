package org.example.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.userservice.dao.RoomDao;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Entity
@Data
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

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    public RoomInfo(String roomName, String description, String coverUrl) {
        this.roomName = roomName;
        this.description = description;
        this.coverUrl = coverUrl;
        this.status = false;
        this.study = false;
        this.entertain = false;
        this.other = false;
    }

    public RoomInfo(Integer roomID, String roomName, Boolean study,Boolean entertain,Boolean other,String description, String coverUrl) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.description = description;
        this.coverUrl = coverUrl;
        this.status = false;
        this.study = study;
        this.entertain = entertain;
        this.other = other;
        this.userInfo = new UserInfo();
    }

    public RoomInfo() {
    }
}
