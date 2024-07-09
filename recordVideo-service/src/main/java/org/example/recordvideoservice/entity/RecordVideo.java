//package org.example.recordvideoservice.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.example.userservice.entity.UserInfo;
//
//@Setter
//@Getter
//@Data
//@Entity
//@Table(name = "video_record")
//public class RecordVideo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "video_id")
//    private Integer recordVideoID;
//
//    @Column(name = "name")
//    private String recordVideoName;
//
//    @Column(name = "description")
//    private String recordVideoIntro;
//
//    @Column(name = "cover_url")
//    private String recordVideoCoverUrl;
//
//    @Column(name = "video_url")
//    private String recordVideoAddress;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    private UserInfo userInfo;
//}
