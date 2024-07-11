package org.example.recommendservice.DTO;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddHotIndex {

    private Integer roomId;
    private int viewCount;
    private int likeCount;
    private int shareCount;
    private int consumptionCount;
    private int messageCount;
    private int newFollowerCount;
    private int sumViewTime;
}
