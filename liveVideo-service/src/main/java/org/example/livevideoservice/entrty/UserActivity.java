package org.example.livevideoservice.entrty;

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

    @Field("enterTime")
    private LocalDateTime enterTime;

    @Field("exitTime")
    private LocalDateTime exitTime;

}
