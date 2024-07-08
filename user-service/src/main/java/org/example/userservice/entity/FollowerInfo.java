package org.example.userservice.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    @Field("followerID")
    private Integer followerID;

    @Field("followeeID")
    private Integer followeeID;

    @Field("followTime")
    private LocalDateTime followTime;
}
