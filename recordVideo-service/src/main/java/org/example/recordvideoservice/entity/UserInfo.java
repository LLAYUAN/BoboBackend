package org.example.recordvideoservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userID; // 这是主键字段

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "selfintro")
    private String selfIntro;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RecordVideo> recordVideo;

}