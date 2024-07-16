package org.example.recordvideoservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class VideoPagePlayingRecordVideo {
    private String videoName;
    private String videoIntro;
    private String videoUrl;
    private Integer ownerUserID;
    private String ownerName;
    private String ownerIntro;
    private String ownerAvatarUrl;
    private LocalDateTime uploadTime;
}
