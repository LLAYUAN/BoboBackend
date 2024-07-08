package org.example.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.userservice.dao.RoomDao;

import java.beans.ConstructorProperties;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    public RoomInfo(String roomName, String description, String coverUrl) {
        this.roomName = roomName;
        this.description = description;
        this.coverUrl = coverUrl;
        this.status = true;
    }

    public RoomInfo() {
    }
}
