package org.example.recordvideoservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class UsersAllRecordVideo {
    private Integer videoID;
    private String videoName;
    private String videoIntro;
    private String ownerName;
    private String imageUrl;
    private LocalDateTime uploadTime;
}
