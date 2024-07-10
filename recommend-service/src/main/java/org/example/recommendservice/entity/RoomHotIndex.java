package org.example.recommendservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

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

    @Field("duration")
    private int duration;

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
}
