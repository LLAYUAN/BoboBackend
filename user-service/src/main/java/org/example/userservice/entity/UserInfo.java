package org.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

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

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "selfintro")
    private String selfIntro;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToOne(mappedBy = "userInfo", fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private RoomInfo roomInfo;

    public UserInfo() {
    }

    public UserInfo(String email, String nickname, String selfIntro, Date birthday) {
//        this.userID = userID;
        this.email = email;
        this.nickname = nickname;
        this.selfIntro = selfIntro;
        this.birthday = birthday;
        this.isAdmin = false;
    }

    public UserInfo(String email,String password){
        this.email = email;
        this.password = password;
        this.isAdmin = false;
    }
}