package org.example.recordvideoservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
}
