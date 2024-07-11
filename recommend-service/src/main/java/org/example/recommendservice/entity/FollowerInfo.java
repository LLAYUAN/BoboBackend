package org.example.recommendservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "followerInfo")
public class FollowerInfo {
    @Id
    private String id;

    @Field("followerId")
    private String followerId;

    @Field("followeeId")
    private String followeeId;

    @Field("followTime")
    private LocalDateTime followTime;
}
