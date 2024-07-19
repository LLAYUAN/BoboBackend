package org.example.livevideoservice.entity;

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
@Document(collection = "userActivity")
public class UserActivity {
    @Id
    private String id;

    @Field("userId")
    private String userId;

    @Field("roomId")
    private String roomId;

    @Field("nickname")
    private String nickname;

    @Field("isEnter")
    private boolean isEnter;
}
