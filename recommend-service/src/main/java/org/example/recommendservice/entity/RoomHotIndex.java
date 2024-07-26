package org.example.recommendservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roomHotIndex")
public class RoomHotIndex {
    @Id
    @Field("_id")
    private Integer roomId;

    @Field("tags")
    private List<Boolean> tags;

    @Field("startTime")
    private LocalDateTime startTime;

    @Field("viewCount")
    private int viewCount;

    @Field("likeCount")
    private int likeCount;

    @Field("shareCount")
    private int shareCount;

    @Field("consumptionCount")
    private int consumptionCount;

    @Field("messageCount")
    private int messageCount;

    @Field("newFollowerCount")
    private int newFollowerCount;

    @Field("sumViewTime")
    private int sumViewTime;

    @Field("hotIndex")
    private int hotIndex;

    public RoomHotIndex(int roomId, List<Boolean> tags){
        this.roomId = roomId;
        this.tags = tags;
        this.startTime = LocalDateTime.now();
        this.viewCount = 0;
        this.likeCount = 0;
        this.shareCount = 0;
        this.consumptionCount = 0;
        this.messageCount = 0;
        this.newFollowerCount = 0;
        this.sumViewTime = 0;
        this.hotIndex = 0;
    }
}
