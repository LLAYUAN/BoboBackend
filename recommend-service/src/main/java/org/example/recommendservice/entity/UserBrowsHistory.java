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
@Document(collection = "userBrowsHistory")
public class UserBrowsHistory {

    @Id
    @Field("_id")
    private String userId; // 用户ID作为文档的_id

    private List<BrowsingRecord> browsingHistory;
}
