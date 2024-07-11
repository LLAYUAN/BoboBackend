package org.example.recordvideoservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UsersAllRecordVideo {
    private Integer videoID;
    private String videoName;
    private String videoIntro;
    private String ownerName;
    private String imageUrl;
}
