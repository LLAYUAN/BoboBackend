package org.example.recordvideoservice.entity;

import jakarta.persistence.*;

public class RecordVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordvideo_id")
    private Integer recordVideoID;

    @Column(name = "recordvideo_name")
    private String recordVideoName;

    @Column(name = "recordvideo_intro")
    private String recordVideoIntro;

    @Column(name = "recordvideo_imageurl")
    private String recordVideoImageUrl;

    @Column(name = "recordvideo_address")
    private String recordVideoAddress;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    private UserInfo
}
